package activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.zhangyang.photoselectdemo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bean.FunctionConfig;
import bean.LocalMedia;
import uk.co.senab.photoview.PhotoViewAttacher;
import util.RotateUtils;


/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.ui
 * email：邮箱->893855882@qq.com
 * data：17/01/18
 */
public class PictureImagePreviewFragment extends Fragment {
    public static final String PATH = "path";
    private List<LocalMedia> selectImages = new ArrayList<>();
    private SimpleDraweeView imageView;
    private String path;

    public static PictureImagePreviewFragment getInstance(String path, List<LocalMedia> medias) {
        PictureImagePreviewFragment fragment = new PictureImagePreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        bundle.putSerializable(FunctionConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.picture_fragment_image_preview, container, false);
        imageView = (SimpleDraweeView) contentView.findViewById(R.id.preview_img);
        path = getArguments().getString(PATH);
        RequestIMG(path);

        return contentView;
    }

    private void RequestIMG(String path) {
        Uri imageUri = Uri.parse(path);
        imageView.setImageURI(imageUri);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(imageUri)
                .setTapToRetryEnabled(true)//设置点击重试是否开启
                .setOldController(imageView.getController())
                .build();
        imageView.setController(controller);
    }

    protected void activityFinish() {
        getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("type", 1).putExtra(FunctionConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectImages));
        getActivity().finish();
    }
}
