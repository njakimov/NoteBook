package com.e.notebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.e.notebook.model.ListNote;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

// Подумайте о функционале вашего приложения заметок. Какие экраны там могут быть, помимо
//основного со списком заметок? Как можно использовать меню и всплывающее меню в вашем
//приложении? Не обязательно сразу пытаться реализовать весь этот функционал, достаточно
//создать макеты и структуру, а реализацию пока заменить на заглушки или всплывающие
//уведомления (Toast). Используйте подход Single Activity для отображения экранов.
//В качестве примера: на главном экране приложения у вас список всех заметок, при нажатии
//на заметку открывается экран с этой заметкой. В меню главного экрана у вас есть иконка
//поиска по заметкам и сортировка. В меню «Заметки» у вас есть иконки «Переслать» (или
//«Поделиться»), «Добавить ссылку или фотографию к заметке».
//2. Создайте боковое навигационное меню для своего приложения и добавьте туда хотя бы один
//экран, например «Настройки» или «О приложении».
//3. * Создайте полноценный заголовок для NavigationDrawer’а. К примеру, аватарка пользователя,
//его имя и какая-то дополнительная информация.
public class MainActivity extends AppCompatActivity {

    private ListNote notes;                                                                                             // текущий список заметок
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        initNote();
        setContentView(R.layout.activity_main);
        readSettings();
        initView();
        setDefaults();
    }

    private void setDefaults() {
        addFragment(new NotesFragment());
    }

    private void initNote() {
        notes = ListNote.getInstance();
        if (notes.size() == 0) {
            notes.addNote("Тема 1", "описание 1");
            notes.addNote("Тема 2", "описание 2");
            notes.addNote("Тема 3", "описание 3");
        }
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
        initTopMenu();
    }

    private void initTopMenu() {
//        FrameLayout noteDetails = findViewById(R.id.noteDetailsContainer);
//        if(noteDetails!=null) {
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, weight);
//            noteDetails.setLayoutParams(params);
//        }
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();                                                                                      // Обработка выбора пункта меню приложения (активити)

        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment(new SettingsFragment());
                setWeightFragmentNoteDetails(0f);
                return true;
            case R.id.action_main:
                addFragment(new NotesFragment());
                setWeightFragmentNoteDetails(1f);
                Toast.makeText(getApplicationContext(), "Добавить заметку", Toast.LENGTH_SHORT).show();
                showNoteDetails(0);
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                setWeightFragmentNoteDetails(0f);
                return true;
        }
        return false;
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);                                                    // Обработка навигационного меню
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        for (int i = countFragments - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private void setWeightFragmentNoteDetails(float weight) {
        FrameLayout noteDetails = findViewById(R.id.noteDetailsContainer);
        if (noteDetails != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, weight);
            noteDetails.setLayoutParams(params);
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();                                                  // Получить менеджер фрагментов
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();                                   // Открыть транзакцию

        if (Settings.isDeleteBeforeAdd) {                                                                               // Удалить видимый фрагмент
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null) {
                fragmentTransaction.remove(fragmentToRemove);
            }
        }

        if (Settings.isAddFragment) {                                                                                   // Добавить фрагмент
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }

        if (Settings.isBackStack) {                                                                                     // Добавить транзакцию в бэкстек
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();                                                                                   // Закрыть транзакцию
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);                                                            // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView();                                                    // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                if(findViewById(R.id.list)!=null) {
                    addFragment(new NotesFragment());
                } else if(findViewById(R.id.listFavorite)!=null) {
                        addFragment(new FavoriteFragment());
                }
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                notes.setTxtSearch(newText);
                return true;
            }
        });
        return true;
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            addFragment(new SettingsFragment());
            setWeightFragmentNoteDetails(0f);
        });

    }

    private void initButtonFavorite() {
        Button buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> {
            addFragment(new FavoriteFragment());
            setWeightFragmentNoteDetails(0f);
        });

    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(v -> {
            addFragment(new NotesFragment());
            setWeightFragmentNoteDetails(1f);
        });

    }

    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (Settings.isBackAsRemove) {
                Fragment fragment = getVisibleFragment(fragmentManager);
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
            } else {
                fragmentManager.popBackStack();
            }
        });
    }

    // Чтение настроек
    private void readSettings() {
// Специальный класс для хранения настроек
        SharedPreferences sharedPref = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
// Считываем значения настроек
        Settings.isBackStack = sharedPref.getBoolean(Settings.IS_BACK_STACK_USED, false);
        Settings.isAddFragment = sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
        Settings.isBackAsRemove = sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd = sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }

    private void showNoteDetails(Integer idNote) {
        if (isLandscape) {
            showLandNoteDetails(idNote);
        } else {
            showPortNoteDetails(idNote);
        }
    }

    private void showPortNoteDetails(Integer idNote) {
        Intent intent = new Intent();                                                                                   // Откроем вторую activity
        intent.setClass(getApplicationContext(), NoteDetailsActivity.class);
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);                                                           // и передадим туда параметры
        startActivity(intent);
    }

    private void showLandNoteDetails(Integer idNote) {
        Intent intent = new Intent();

        intent.setClass(getApplicationContext(), NoteDetailsActivity.class);                                                // Откроем вторую activity
        intent.putExtra(NoteDetailsFragment.ID_NOTE, idNote);                                                           // и передадим туда параметры
        startActivity(intent);

        NoteDetailsFragment detail = NoteDetailsFragment.newInstance(idNote);

        FragmentManager fragmentManager = getSupportFragmentManager();                                                  // Выполняем транзакцию по замене фрагмента
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.noteDetailsContainer, detail);                                                 // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}