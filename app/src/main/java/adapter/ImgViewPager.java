package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangyang.photoselectdemo.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

import bean.LocalMedia;

/**
 * Created by zhangyang on 17/3/16.
 */

public class ImgViewPager extends PagerAdapter {
    private Context context;
    private List<LocalMedia> list;

    public ImgViewPager(Context context, List<LocalMedia> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = getItem(container, position);
        container.addView(view);
        return view;
    }

    private View getItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.picture_fragment_image_preview, null);
        SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.preview_img);
        Uri imageUri = Uri.parse("file://"+ list.get(position).getPath());
        int width = 300, height = 300;
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(imageView.getController())
                .setImageRequest(request)
                .setTapToRetryEnabled(true)//设置点击重试是否开启
                .build();
        imageView.setController(controller);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
