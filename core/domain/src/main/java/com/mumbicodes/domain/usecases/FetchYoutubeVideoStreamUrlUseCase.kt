package com.mumbicodes.domain.usecases

import android.app.Application
import android.util.SparseArray
import com.maxrave.kotlinyoutubeextractor.State
import com.maxrave.kotlinyoutubeextractor.YTExtractor
import com.maxrave.kotlinyoutubeextractor.YtFile
import com.maxrave.kotlinyoutubeextractor.bestQuality
import com.maxrave.kotlinyoutubeextractor.getVideoOnly

class FetchYoutubeVideoStreamUrlUseCase(
    private val appContext: Application
) {
    suspend operator fun invoke(youtubeId: String): String? {
        val yt = YTExtractor(
            con = appContext.applicationContext,
            CACHING = true,
            LOGGING = true,
            retryCount = 3
        )
        var ytFiles: SparseArray<YtFile>? = null
        var streamUrl: String? = null

        yt.extract(youtubeId)
        if (yt.state == State.SUCCESS) {
            ytFiles = yt.getYTFiles()
            val ytFile = ytFiles?.getVideoOnly()?.bestQuality()
            // Get stream URL
            streamUrl = ytFile?.url
        }

        return streamUrl
    }
}