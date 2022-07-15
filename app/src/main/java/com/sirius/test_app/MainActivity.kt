package com.sirius.test_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import de.hdodenhof.circleimageview.CircleImageView
import me.zhanghai.android.materialratingbar.MaterialRatingBar


class MainActivity : AppCompatActivity() {

    private lateinit var headerPoster: ImageView
    private lateinit var appLogo: ImageView
    private lateinit var appName: TextView
    private lateinit var appRatingBar1: MaterialRatingBar
    private lateinit var appReviewsNum: TextView
    private lateinit var categories: ChipGroup
    private lateinit var appDescription: TextView
    private lateinit var appRatingNum: TextView
    private lateinit var appRatingBar2: MaterialRatingBar
    private lateinit var appReviewsNumExtended:TextView
    private lateinit var reviewsContainer: LinearLayout
    private lateinit var btnAction: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.apply {
            setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                     WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        headerPoster   = findViewById(R.id.iv_header_bg_img)
        appLogo        = findViewById(R.id.iv_appLogo)
        appName        = findViewById(R.id.tv_appName)
        appRatingBar1  = findViewById(R.id.rb_appRatingBar1)
        appReviewsNum  = findViewById(R.id.tv_reviewsNum)
        categories     = findViewById(R.id.cg_categories)
        appDescription = findViewById(R.id.tv_appDescription)
        appRatingNum   = findViewById(R.id.tv_ratingNum)
        appRatingBar2  = findViewById(R.id.rb_appRatingBar2)
        appReviewsNumExtended = findViewById(R.id.tv_reviewsNumExtended)
        reviewsContainer      = findViewById(R.id.reviews_container)
        btnAction = findViewById(R.id.btn_action)

        setAppDetails(DataModel())
    }

    private fun setAppDetails(model: DataModel) {
        Glide.with(this).load(model.image).into(headerPoster)
        Glide.with(this).load(model.logo).into(appLogo)

        appName.text = model.name
        appRatingBar1.rating = model.rating
        appRatingBar2.rating = model.rating
        appReviewsNum.text   = model.gradeCnt

        for (category in model.tags) {
            val chip = layoutInflater.inflate(
                R.layout.chip_single_store_category, categories, false) as Chip
            chip.text = category
            categories.addView(chip as View)
        }

        appDescription.text = model.description
        appRatingNum.text = model.rating.toString()
        appReviewsNumExtended.text = resources.getString(
            R.string.text_reviewsNumExtended, model.gradeCnt)

        for (review in model.reviews) {
            val layout = layoutInflater.inflate(
                R.layout.layout_single_store_review,
                reviewsContainer, false) as ConstraintLayout

            val profilePic = layout.findViewById<CircleImageView>(R.id.iv_profilePicture)
            val name = layout.findViewById<TextView>(R.id.tv_name)
            val date = layout.findViewById<TextView>(R.id.tv_date)
            val text = layout.findViewById<TextView>(R.id.tv_text)

            name.text = review.userName
            date.text = review.date
            text.text = review.message
            profilePic.setImageResource(R.drawable.dota2_logo)

            Glide
                .with(this)
                .load(review.userImage)
                .centerCrop()
                .placeholder(R.drawable.dota2_logo)
                .into(profilePic)

            reviewsContainer.addView(layout)
        }

        btnAction.text = model.action.name
    }
}