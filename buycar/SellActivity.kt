package com.example.buycar
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buycar.CarContract
import com.example.buycar.CarDbHelper
import com.example.buycar.R

class SellActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var selectedImageUris: MutableList<Uri>
    private lateinit var imageRecyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var selectedImageUri: Uri
    private lateinit var sell: TextView
    private lateinit var selectedPhotosLinearLayout: LinearLayout
    private lateinit var photoHorizontalScrollView: HorizontalScrollView
    private lateinit var addPhotoImageView: ImageView
    private lateinit var addMorePhotosButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sell)

        // Request permission to read external storage (required for accessing images)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PICK_IMAGE_REQUEST
            )
        }

        val dbHelper = CarDbHelper(this)
        val db = dbHelper.writableDatabase

        sell = findViewById(R.id.sell)
        addPhotoImageView = findViewById(R.id.addPhotoImageView)
        selectedPhotosLinearLayout = findViewById(R.id.selectedPhotosLinearLayout)
        photoHorizontalScrollView = findViewById(R.id.photoHorizontalScrollView)
        addMorePhotosButton = findViewById(R.id.addMorePhotosButton)
        val titleEditText = findViewById<EditText>(R.id.TitleEditText)
        val descriptionEditText = findViewById<EditText>(R.id.DescriptionEditText)
        val priceEditText = findViewById<EditText>(R.id.PriceEditText)
        val postButton = findViewById<Button>(R.id.postButton)

        selectedImageUris = mutableListOf()
        imageRecyclerView = findViewById(R.id.imageRecyclerView)
        imageAdapter = ImageAdapter(selectedImageUris)

        imageRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageRecyclerView.adapter = imageAdapter


        addPhotoImageView.setOnClickListener {
            openImagePicker()
        }

        postButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()
            val priceString = priceEditText.text.toString().trim()

            if (!title.isEmpty() && !description.isEmpty() && !priceString.isEmpty()) {
                val price = priceString.toDouble()

                if (::selectedImageUris.isInitialized && selectedImageUris.isNotEmpty()) {
                    for (imageUri in selectedImageUris) {
                        val imageUriString = selectedImageUri.toString()

                        val values = ContentValues()
                        values.put(CarContract.CarEntry.COLUMN_TITLE, title)
                        values.put(CarContract.CarEntry.COLUMN_DESCRIPTION, description)
                        values.put(CarContract.CarEntry.COLUMN_PRICE, price)
                        values.put(CarContract.CarEntry.COLUMN_PHOTO, imageUriString)

                        val newRowId = db.insert(CarContract.CarEntry.TABLE_NAME, null, values)
                        if (newRowId != -1L) {
                            // Data insertion successful.
                            // You can show a toast or perform any other action to notify the user.
                            Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, CarList::class.java))
                        } else {
                            // Data insertion failed. Handle the error if needed.
                            Toast.makeText(this, "Data insertion failed.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    imageAdapter.notifyDataSetChanged()
                    showImageList()


                    addMorePhotosButton.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    private fun showImageList() {
        selectedPhotosLinearLayout.removeAllViews()

        for (imageUri in selectedImageUris) {
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.photo_width),
                resources.getDimensionPixelSize(R.dimen.photo_height)
            )
            imageView.setImageURI(imageUri)
            selectedPhotosLinearLayout.addView(imageView)
        }

        // Show the selected photos layout
        photoHorizontalScrollView.visibility = View.VISIBLE
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data!!
            selectedImageUris.add(0, imageUri) // Add at the beginning
            imageAdapter.notifyDataSetChanged()
            showImageList()

            // Set the selected photo at the top
            addPhotoImageView.setImageURI(imageUri)

            addMorePhotosButton.visibility = View.VISIBLE
        }
    }
}
