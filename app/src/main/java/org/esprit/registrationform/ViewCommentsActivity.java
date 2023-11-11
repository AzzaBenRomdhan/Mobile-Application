package org.esprit.registrationform;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import org.esprit.registrationform.MyDataBaseHelper;

import java.util.ArrayList;

public class ViewCommentsActivity extends AppCompatActivity {
    private ListView listViewComments;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> commentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        // Initialize the ListView
        listViewComments = findViewById(R.id.listViewComments);

        // Initialize the adapter
        commentData = getCommentData();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commentData);

        // Bind the adapter to the ListView
        listViewComments.setAdapter(adapter);

        // Configurez un écouteur pour la suppression des commentaires lors d'un clic long
        listViewComments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // Affichez une boîte de dialogue pour confirmer la suppression
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewCommentsActivity.this);
                builder.setMessage("Do you want to delete the comment?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Récupérez le commentaire à partir de la liste
                        String comment = commentData.get(position);

                        // Supprimez le commentaire de la base de données
                        MyDataBaseHelper dbHelper = new MyDataBaseHelper(ViewCommentsActivity.this);
                        dbHelper.deleteComment(comment);

                        // Retirez le commentaire de la liste et mettez à jour l'adaptateur
                        commentData.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();

                return true;
            }
        });

    }

    // Logic to retrieve data from the database
    private ArrayList<String> getCommentData() {
        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this);
        return dbHelper.getAllComments();
    }
}
