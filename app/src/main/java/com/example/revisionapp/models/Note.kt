package com.example.revisionapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Note(
    var noteId: String? = null,
    var title: String? = null,
    var timeStamp: String? = null,
    var body: String? = null,
): Parcelable