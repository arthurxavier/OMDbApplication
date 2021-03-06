package br.com.zup.omdbapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.zup.omdbapplication.R;
import br.com.zup.omdbapplication.activities.DescriptionActivity;
import br.com.zup.omdbapplication.context.Contexto;
import br.com.zup.omdbapplication.production.Imdb;

/**
 * Created by arthur on 31/01/17.
 */

public class CustomAdapter extends RecyclerView.Adapter{
    private ArrayList<Imdb> lista;
    private Context context;
    private Activity pai;

    public CustomAdapter(ArrayList<Imdb> lista, Context context, Activity pai){
        this.lista = lista;
        this.context = context;
        this.pai = pai;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview, parent, false);
        CustomRecyclerViewHolder holder = new CustomRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        final CustomRecyclerViewHolder holder = (CustomRecyclerViewHolder) viewHolder;
        final Imdb imdb = lista.get(position);
        holder.txtTitulo.setText(imdb.getTitle());
        holder.txtAno.setText(imdb.getYear());

        //se o caminho for null, a producao nao esta salva
        //se esta salva, basta pega-la no bd
        if(imdb.getImagemPath()==null){
            holder.imgPoster.setImageBitmap(imdb.getImagem());
        } else {
            String path = imdb.getImagemPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            holder.imgPoster.setImageBitmap(bitmap);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contexto.context(), DescriptionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imdb",imdb.getImdbID());
                intent.putExtra("contexto",context.getClass().toString());
                Contexto.context().startActivity(intent);
                if(context.getClass().toString().equals("class br.com.zup.omdbapplication.activities.activity.MainActivity") && holder.bt_Salvar.getText().toString().equals("Remover")) {
                    pai.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    public class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView txtTitulo;
        private TextView txtAno;
        private ImageView imgPoster;
        private Button bt_Salvar;

        public CustomRecyclerViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.card_view);
            txtTitulo = (TextView) view.findViewById(R.id.cardTitulo);
            txtAno = (TextView) view.findViewById(R.id.cardAno);
            imgPoster = (ImageView) view.findViewById(R.id.cardImage);
            bt_Salvar = (Button) view.findViewById(R.id.btnSalvar);
        }
    }
}
