package codelounge.app.com.Model

data class FirebaseItem(
    val title: String,
    val content: String
)

data class FirebaseSection(
    val sectionTitle: String,
    val items: List<FirebaseItem>
)