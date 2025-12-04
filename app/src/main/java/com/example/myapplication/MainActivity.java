package com.example.myapplication;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.ui.Tickets.Ticket;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    private AppBarConfiguration appBarConfiguration;
    private boolean isUser;
    private SearchView searchView;
    private ListView resultView;
    //private ArrayAdapter<String> searchAdapter;
    private java.util.List<String> allResults;
    private java.util.List<String> filteredResults;
    private SearchResultAdapter searchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reset the tickets database on app start
        resetAndInitializeTicketsDatabase();

        isUser = getIntent().getBooleanExtra("is_user", true);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        fab = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);
        resultView = findViewById(R.id.searchResultsView);


        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null) {
            throw new IllegalStateException("NavHostFragment not found");
        }
        NavController navController = navHostFragment.getNavController();

        configureNavigation(navController);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        navigationView.setNavigationItemSelectedListener(item -> handleDrawerSelection(item, navController));
        fab.setOnClickListener(view -> showBottomDialog());

        //----------Search Bar Stuff----------
        resultView.setVisibility(View.GONE);

        allResults = java.util.Arrays.asList(
                "Sample Event 123",
                "Sample Event 456",
                "Sample Event 789",
                "Sample Event 147",
                "Sample Event 569"
        );

        filteredResults = new java.util.ArrayList<>();

        searchAdapter = new SearchResultAdapter(
                this,
                filteredResults
        );

        resultView.setAdapter(searchAdapter);

        resultView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = filteredResults.get(position);
            Toast.makeText(MainActivity.this, "Clicked: " + selected, Toast.LENGTH_SHORT).show();

            //Navigate to the event's page

            searchView.setQuery("", false);
            searchView.clearFocus();
            hideSearchResults();
        });

        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showSearchResults();
            } else {
                hideSearchResults();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterResults(newText);
                return true;
            }
        });
    }

    private void resetAndInitializeTicketsDatabase() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        List<Ticket> mockTickets = new ArrayList<>();
        mockTickets.add(new Ticket("1", "VerTech Gala Normal", 0.00, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        mockTickets.add(new Ticket("2", "VerTech Gala Premium", 1.99, 1, "OCT", "24", "7:00 PM", "VerTech Gala", false));
        mockTickets.add(new Ticket("3", "c2 Hacks", 5.00, 1, "NOV", "15", "5:00 PM", "c2 Hacks", false));

        Map<String, Object> ticketMap = new HashMap<>();
        for (Ticket ticket : mockTickets) {
            ticketMap.put(ticket.getId(), ticket);
        }

        // Overwrite the entire "tickets" node with the fresh mock data
        db.child("tickets").setValue(ticketMap);
    }

    //----------Search Bar Stuff----------
    private void showSearchResults() {
        resultView.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
    }

    private void hideSearchResults() {
        resultView.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
    }

    private void filterResults(String query) {
        filteredResults.clear();

        if (query != null) {
            String lower = query.toLowerCase().trim();
            if (!lower.isEmpty()) {
                for (String item : allResults) {
                    if (item.toLowerCase().contains(lower)) {
                        filteredResults.add(item);
                    }
                }
            }
        }

        searchAdapter.notifyDataSetChanged();
    }

    //----------Navigation Stuff----------
    private boolean handleDrawerSelection(@NonNull MenuItem item, @NonNull NavController navController) {
        Toast.makeText(this, item.getTitle() + " pressed", Toast.LENGTH_SHORT).show();
        boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
        drawerLayout.closeDrawers();
        return handled;
    }

    private void configureNavigation(@NonNull NavController navController) {
        if (isUser) {
            navController.setGraph(R.navigation.mobile_navigation_user);
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_student);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_drawer_student);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.feedFragment,
                    R.id.eventsStudentFragment,
                    R.id.ticketsFragment,
                    R.id.profileFragment
            ).setOpenableLayout(drawerLayout).build();
        } else {
            navController.setGraph(R.navigation.mobile_navigation_club);
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_club);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.nav_drawer_club);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.postsFragment,
                    R.id.eventsClubFragment,
                    R.id.ticketSalesFragment,
                    R.id.clubPageFragment
            ).setOpenableLayout(drawerLayout).build();
        }
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout videoLayout = dialog.findViewById(R.id.layoutVideo);
        LinearLayout shortsLayout = dialog.findViewById(R.id.layoutShorts);
        LinearLayout liveLayout = dialog.findViewById(R.id.layoutLive);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Upload a Video is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        shortsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Create a short is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        liveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Go live is Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }
}
