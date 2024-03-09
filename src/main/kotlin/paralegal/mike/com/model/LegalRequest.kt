package paralegal.mike.com.model

import kotlinx.serialization.Serializable

/**
 * Used we already have a data file locally that we need to analyze
 */
@Serializable
data class PreDefined(
    val content: String,
    val instructions: String
)