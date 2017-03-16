package adapter;

import android.content.Context;
import android.net.Uri;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhangyang.photoselectdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bean.LocalMediaFolder;

/**
 * Created by zhangyang on 17/3/15.
 */

public class PopAdapter extends BaseAdapter {
    private Context context;
    private List<LocalMediaFolder> list;
    private PopItemClickListener popItemClickListener;
    public PopAdapter(Context context, List<LocalMediaFolder> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.picture_album_folder_item, null);
            vh = new ViewHolder();
            vh.first_image = (SimpleDraweeView) convertView.findViewById(R.id.first_image);
            vh.tv_folder_name = (TextView) convertView.findViewById(R.id.tv_folder_name);
            vh.image_num = (TextView) convertView.findViewById(R.id.image_num);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        LocalMediaFolder folder = list.get(position);
        vh.tv_folder_name.setText(folder.getName());

        Uri imageUri = Uri.parse("file://" + folder.getFirstImagePath());
        vh.first_image.setImageURI(imageUri);
        vh.image_num.setText(folder.getImageNum() + "张");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popItemClickListener != null){
                    notifyDataSetChanged();
                    popItemClickListener.OnClick(list.get(position));
                }
            }
        });
        return convertView;
    }
    static class ViewHolder {
        SimpleDraweeView first_image;
        TextView tv_folder_name, image_num;
    }
   public interface PopItemClickListener{
       void OnClick(LocalMediaFolder  folder);
   }

    public void setPopItemClickListener(PopItemClickListener popItemClickListener) {
        this.popItemClickListener = popItemClickListener;
    }
}
