package com.example.capstonefinaldiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


// flask 서버에서 플레이리스트 받기
public class MusicAdapter extends RecyclerView.Adapter {

    //리사이클러뷰에 넣을 데이터 리스트
    private List<PlaylistItem> playlistItems;
    // Firebase 오디오 파일 URL을 저장할 리스트
    private Context context;

    // 리스너 객체 참조를 저장하는 변수
    private OnIconClickListener listener = null;

    /**
     * 커스텀 이벤트 리스너
     * 클릭이벤트를 Adapter에서 구현하기에 제약이 있기 때문에 Activity 에서 실행시키기 위해 커스텀 이벤트 리스너를 생성함.
     * 절차
     * 1.커스텀 리스너 인터페이스 정의
     * 2. 리스너 객체를 전달하는 메서드와 전달된 객체를 저장할변수 추가
     * 3. 아이템 클릭 이벤트 핸들러 메스드에서 리스너 객체 메서드 호출
     * 4. 액티비티에서 커스텀 리스너 객체 생성 및 전달(MainActivity.java 에서 audioAdapter.setOnItemClickListener() )
     */
    // 1.커스텀 리스너 인터페이스 정의
    public interface OnIconClickListener {
        void onItemClick(View view, int position);
    }

    // 2. 리스너 객체를 전달하는 메서드와 전달된 객체를 저장할변수 추가
    public void setOnItemClickListener(OnIconClickListener listener) {
        this.listener = listener;
    }

    //생성자를 통하여 데이터 리스트 context를 받음
    public MusicAdapter(Context context, List<PlaylistItem> playlistItems) {
        this.context = context;
        this.playlistItems = playlistItems;
    }

    @Override
    public int getItemCount(){
        return playlistItems.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder holder1 = (MyViewHolder)holder;
        PlaylistItem item = playlistItems.get(position);
        holder1.title.setText(item.getTitle());
        holder1.artist.setText(item.getArtist());
        // 이미지 로드 및 설정
        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.baseline_image_24) // 이미지가 로드되기 전에 표시할 이미지
                .error(R.drawable.baseline_hide_image_24) // 이미지 로드 오류 시 표시할 이미지
                .into(((MyViewHolder) holder).album_img);
        //음악 재생 코드
        /**
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int pos = holder.getAdapterPosition(); // 인덱스를 동적으로 가져오기
                //if (listener != null && pos != RecyclerView.NO_POSITION) {
                //    listener.onItemClick(view, pos);
                }
        }); */
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton audioBtn;
        public TextView title, artist;
        public ImageView album_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            audioBtn = itemView.findViewById(R.id.playImageBtn);
            title = itemView.findViewById(R.id.music_Title);
            artist = itemView.findViewById(R.id.music_artist);
            album_img = itemView.findViewById(R.id.album_img);
            /**
            audioBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //3. 아이템 클릭 이벤트 핸들러 메스드에서 리스너 객체 메서드 호출
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (listener != null) {
                            listener.onItemClick(view, pos) ;
                        }
                    }
                }
            }); */
        }
    }
}
