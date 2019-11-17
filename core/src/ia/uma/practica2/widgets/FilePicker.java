package ia.uma.practica2.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Arrays;

/**
 * Based on code by @BurningHand
 * Added input name by @jesusmartinoza
 * </br>
 * http://badlogicgames.com/forum/viewtopic.php?f=11&t=11347
 */
public class FilePicker extends Dialog {

    private Skin skin;
    private FileHandle directory;
    private FileHandle file;
    private ClickListener fileClickListener;
    protected Table table;

    public FilePicker(String title, Skin skin) {
        super(title, skin);
        this.getCell(getButtonTable()).expandX().fill();
        this.getButtonTable().defaults().expandX().fill();
        setFileClickListener();

        this.button("Cancel", "Cancel");
        this.button("OK", "OK");

        this.setModal(true);
        this.skin = skin;
        this.table = new Table().top().left();
    }

    private void onFileClicked(String filename) {
        //System.out.print(filename);
    }

    /**
     * Click listener event of table item. It handles if it has to go to
     * another directory or select a file.
     */
    protected void setFileClickListener() {
        fileClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Label target = (Label) event.getTarget();
                FileHandle handle = directory.child(target.getName());
                if (handle.isDirectory()) {
                    setDirectory(handle);
                } else {
                    setFile(handle);
                    onFileClicked(target.getName());
                }
            }
        };
    }

    /**
     * Get selected file handler.
     * @return
     */
    public FileHandle getFile() {
        return file;
    }

    /**
     * Set the root directory.
     * @param directory
     */
    public void setDirectory(FileHandle directory) {
        if (this.directory != directory) {
            this.directory = directory;
            this.file = null;
            this.getContentTable().reset();
            table = new Table().top().left();
            buildList();
        }
    }

    /**
     * Set selected file.
     * @param file Handler
     */
    public void setFile(FileHandle file) {
        if (this.file != file) {
            if (this.file != null) {
                Label label = this.findActor(this.file.name());
                label.setColor(Color.WHITE);
            }
            Label label = this.findActor(file.name());
            label.setColor(Color.PURPLE);
            this.file = file;
        }
    }

    /**
     * Populate dialog table with files names.
     */
    protected void buildList() {
        FileHandle[] files = directory.list();
        Arrays.sort(files, (o1, o2) -> {
            if (o1.isDirectory() && !o2.isDirectory()) {
                return -1;
            }
            if (o2.isDirectory() && !o1.isDirectory()) {
                return +1;
            }
            return o1.name().compareToIgnoreCase(o2.name());
        });
        ScrollPane pane = new ScrollPane(null, skin);
        ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle();
        style.vScroll = skin.getDrawable("scrollbar-android-vertical");
        style.vScrollKnob = skin.getDrawable("scrollbar-android-vertical-knob");
        pane.setStyle(style);
        pane.setScrollingDisabled(true, false);
        table.defaults().left();

        // File chooser
        table.row();
        for (FileHandle file : files) {
            table.row();

            if(!file.isDirectory() && !file.name().equals("remove_ads.txt")) {
                Label label = new Label(file.name(), skin);
                label.setName(file.name());
                label.addListener(fileClickListener);
                table.add(label).padLeft(4).padBottom(8).padTop(8).expandX().left().fillX();
            }
        }
        pane.setWidget(table);
        this.getContentTable().pad(5);
        this.getContentTable().add(pane).size(240, 140).expand().fill();
    }

    @Override
    public Dialog show(Stage stage) {
        return super.show(stage);
    }
}