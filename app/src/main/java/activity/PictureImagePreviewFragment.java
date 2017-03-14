package activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

 * data：17/01/18
 */
public class PictureImagePreviewFragment extends Fragment {
    public static final String PATH = "path";
    private List<LocalMedia> selectImages = new ArrayList<>();
    private ImageView imageView, ivLoading;
    private PhotoViewAttacher mAttacher;
    private LinearLayout llAgain;
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
        imageView = (ImageView) contentView.findViewById(R.id.preview_image);
        ivLoading = (ImageView) contentView.findViewById(R.id.ivloading);
        llAgain = (LinearLayout) contentView.findViewById(R.id.llAgain);
        mAttacher = new PhotoViewAttacher(imageView);
        llAgain.setVisibility(View.GONE);

        //被选中的图片
        selectImages = (List<LocalMedia>) getArguments().getSerializable(FunctionConfig.EXTRA_PREVIEW_SELECT_LIST);
        Log.e("asdsa", selectImages.size() + "");


        path = getArguments().getString(PATH);
        RequestIMG(path);
        llAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAgain.setVisibility(View.GONE);
                RequestIMG(path);
            }
        });
        mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (getActivity() instanceof PicturePreviewActivity) {
                    activityFinish();
                } else {
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, R.anim.toast_out);
                }
            }
        });
        return contentView;
    }

    private void RequestIMG(String path) {
        Glide.with(getContext())
                .load(path)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>(480, 800) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        RotateUtils.rotate(getActivity(), ivLoading);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        RotateUtils.stopRotateAndGONE(getActivity(), ivLoading);
                        imageView.setImageBitmap(resource);
                        mAttacher.update();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        RotateUtils.stopRotateAndGONE(getActivity(), ivLoading);
                        llAgain.setVisibility(View.VISIBLE);
                    }
                });
    }

    protected void activityFinish() {
        getActivity().setResult(getActivity().RESULT_OK, new Intent().putExtra("type", 1).putExtra(FunctionConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) selectImages));
        getActivity().finish();
    }
}
