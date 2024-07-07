package state

import data.items.Item


sealed class LoadState {
    data object onIdle:LoadState()
    data object onLoading :LoadState()
    data class onSuccess(val result : ArrayList<Item>):LoadState()
    data class onError(val message :String?="Something went wrong try again"):LoadState()
}