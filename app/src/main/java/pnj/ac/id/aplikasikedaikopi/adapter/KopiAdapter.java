package pnj.ac.id.aplikasikedaikopi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import pnj.ac.id.aplikasikedaikopi.R;
import pnj.ac.id.aplikasikedaikopi.model.KopiModel;

public class KopiAdapter extends ArrayAdapter<KopiModel> {

    Context context;
    int resource;

    public KopiAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;

        if(convertView==null){
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            holder.imageKopi = convertView.findViewById(R.id.imgKopi);
            holder.txtDesc = convertView.findViewById(R.id.txtDesc);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            convertView.setTag(holder);
        }else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtTitle.setText(getItem(position).getTitle());
        holder.txtDesc.setText(getItem(position).getDescription());
        Picasso.get().load(getItem(position).getImage()).into(holder.imageKopi);

        return convertView;
    }

    class Holder {
        ImageView imageKopi;
        TextView txtTitle,txtDesc;
    }
}
