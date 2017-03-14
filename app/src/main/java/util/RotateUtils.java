package util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.zhangyang.photoselectdemo.R;


public class RotateUtils {

	public static void rotate(Context context, ImageView iv){
		Animation operatingAnim = AnimationUtils.loadAnimation(context, R.anim.operate);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);  
		if (operatingAnim != null) {
			iv.setVisibility(View.VISIBLE);
			iv.startAnimation(operatingAnim);  
		}  
	}
	public static void stopRotateAndGONE(Context context, ImageView iv){
		iv.clearAnimation();
		iv.setVisibility(View.GONE);
	}
	
}
