package com.alamin.myapplication

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

private const val TAG = "MainWheelActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var wheel: ImageView
    private val sectors: IntArray = intArrayOf(5,4,3,2,1,8,7,6)
    private lateinit var sectorDegrees: IntArray
    private var randomSectorIndex = 0
    private var spinning = false

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wheel = findViewById(R.id.wheel)
        sectorDegrees = IntArray(sectors.size)
        generateSectorDegrees()

        wheel.setOnClickListener {
            if (!spinning) {
                spin()
                spinning = true
            }
        }
    }

    private fun spin() {
        //get any random sector index
        //Change Here If you want to set a particular sector. Ex. 3,It will show 1 or If 1, It will show 7
        val index = sectors.indexOf(4)
        Log.d(TAG, "spin: $index")
        randomSectorIndex = random.nextInt(sectors.size)// the bound in exclusive// For Target Value sectors.size - (index+1)
        //generate a random degree to spin the wheel
        val randomDegree: Int = generateRandomDegreeToSpinTo()
        Log.d(TAG, "spin: $randomDegree")
        //do actual spinning use the rotation animation
        val rotateAnimation = RotateAnimation(
            0f,
            randomDegree.toFloat(),
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f,
            RotateAnimation.RELATIVE_TO_SELF,
            0.5f
        )
        //time for spinning
        rotateAnimation.duration = 3000 //3 sec
        rotateAnimation.fillAfter = true // not reset starting position wheel after spinning
        rotateAnimation.interpolator = DecelerateInterpolator() // start with high speed, slow down at the end

        rotateAnimation.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val point = sectors[sectors.size - (randomSectorIndex+1)]
                Toast.makeText(this@MainActivity, "$point", Toast.LENGTH_SHORT).show()
                spinning = false
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        wheel.startAnimation(rotateAnimation)


    }

    private fun generateRandomDegreeToSpinTo(): Int {
        return (360 * sectors.size) + sectorDegrees[randomSectorIndex]
    }

    private fun generateSectorDegrees() {
        val sectorDegree = 360 / sectors.size
        Log.d(TAG, "generateSectorDegrees: Degree $sectorDegree")
        for (i in 0 until sectors.size) {
            sectorDegrees[i] = (i+1) * sectorDegree
            Log.d(TAG, "generateSectorDegrees: "+sectorDegrees[i])
        }
    }
}