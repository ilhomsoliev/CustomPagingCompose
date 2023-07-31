package com.ilhomsoliev.paging.paging

const val NETWORK_PAGE_SIZE_DEFAULT = 20 //15
const val NETWORK_PAGE_FIRST_TIME_MULTIPLIER_DEFAULT = 3
const val NETWORK_ITEMS_THRESHOLD_DEFAULT = 6

fun getPagingOffset(page: Int, limit: Int = NETWORK_PAGE_SIZE_DEFAULT): Int =
    ((page - 1) * NETWORK_PAGE_SIZE_DEFAULT) + if (page != 1) (NETWORK_PAGE_FIRST_TIME_MULTIPLIER_DEFAULT - 1) * limit else 0

fun getPagingLimit(
    page: Int,
    limit: Int = NETWORK_PAGE_SIZE_DEFAULT,
    isDefaultPerPage: Boolean = false
): Int =
    if (page == 1 && !isDefaultPerPage) limit * NETWORK_PAGE_FIRST_TIME_MULTIPLIER_DEFAULT else if (!isDefaultPerPage) limit
    else limit

fun getItemsAmountInPage(page: Int, limit: Int = NETWORK_PAGE_SIZE_DEFAULT): Int =
    if (page == 1) getPagingLimit(1)
    else getPagingLimit(1) + (page - 1) * getPagingLimit(2)

/*
val offset = getPagingOffset(page)
val limit = getPagingLimit(page)
*/

data class PagingState<T>(
    val list: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val firstLoading: Boolean = false,
    val currentPage: Int = 0,
    val isFinished: Boolean = false,
    val error: String? = null,
    var onFetchData: (suspend (Int) -> List<T>)? = null,
    var onUpdate: (suspend (PagingState<T>) -> Unit)? = null,
    var onError: (suspend (String?) -> Unit)? = null,
) {
    @Throws(Exception::class)
    suspend fun loadNextPage() {
        if (!isCallbackSpecified()) throw Exception("onUpdate not specified")
        onUpdate?.let { onUpdate ->
            onFetchData?.let { onFetchData ->
                try {
                    var state = this
                    val newPage = this.currentPage + 1
                    if (newPage == 1) state =
                        state.copy(firstLoading = true).let { onUpdate(it);it }
                    state = state.copy(isLoading = true).let { onUpdate(it);it }
                    state = state.copy(currentPage = newPage).let { onUpdate(it);it }
                    val result = onFetchData(newPage)
                    val newList = this.list.toMutableList()
                    newList.addAll(result)
                    state = state.copy(
                        list = newList,
                        isLoading = false,
                        isFinished = result.size != NETWORK_PAGE_SIZE_DEFAULT,
                        firstLoading = false,
                    ).let { onUpdate(it);it }

                } catch (e: Exception) {
                    onError?.let { it(e.message) }
                    onUpdate(this.copy(isLoading = false));

                }
            }
        }
    }

    suspend fun onNewPosition(index: Int, threshold: Int = NETWORK_ITEMS_THRESHOLD_DEFAULT) {
        if (index + threshold >= list.size && !isLoading && !firstLoading && !isFinished) {
            loadNextPage()
        }
    }

    private fun isCallbackSpecified() = onUpdate != null && onFetchData != null

}