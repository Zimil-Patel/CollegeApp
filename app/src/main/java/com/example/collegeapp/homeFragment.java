package com.example.collegeapp;

import android.animation.LayoutTransition;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    private CardView ugCard, pgCard, diplomaCard, phdCard, certificateCard;
    private RecyclerView ugRecycler, pgRecycler, diplomaRecycler, phdRecycler, certificateRecycler;
    private ArrayList<ProgramsData> ugList,pgList, diplomaList, phdList, certificateList;
    private ProgramsAdapter adapter;
    private LinearLayout uglayout, pglayout, diplomalayout, phdlayout, certificateLayout;

    public homeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageSlider imageSlider = v.findViewById(R.id.imageSlider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels);

        ugCard = v.findViewById(R.id.UGPrograms);
        pgCard = v.findViewById(R.id.PGPrograms);
        diplomaCard = v.findViewById(R.id.DiplomaPrograms);
        phdCard = v.findViewById(R.id.PhDPrograms);
        certificateCard = v.findViewById(R.id.CertificatePrograms);

        uglayout = v.findViewById(R.id.UGlayout);
        pglayout = v.findViewById(R.id.pglayout);
        diplomalayout = v.findViewById(R.id.diplomalayout);
        phdlayout = v.findViewById(R.id.phdlayout);
        certificateLayout = v.findViewById(R.id.certificatelayout);

        ugRecycler = v.findViewById(R.id.UGrecycler);
        pgRecycler = v.findViewById(R.id.PGrecycler);
        diplomaRecycler = v.findViewById(R.id.Diplomarecycler);
        phdRecycler = v.findViewById(R.id.Phdrecycler);
        certificateRecycler = v.findViewById(R.id.Certificaterecycler);

        uglayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        pglayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        diplomalayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        phdlayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        certificateLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        ugCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vv = (ugRecycler.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(uglayout, new AutoTransition());
                ugRecycler.setVisibility(vv);

                ugList = new ArrayList<>();
                ugList.add(new ProgramsData("B.Sc Botany","6"));
                ugList.add(new ProgramsData("B.Sc Physics","60"));
                ugList.add(new ProgramsData("B.Sc Zoology","60"));
                ugList.add(new ProgramsData("B.Sc Mathematics","60"));
                ugList.add(new ProgramsData("B.Sc Integrated Medical laboratory technology","180"));
                ugList.add(new ProgramsData("B.Sc Medical laboratory technology","60"));
                ugList.add(new ProgramsData("B.Sc Microbiology","60"));
                ugList.add(new ProgramsData("B.Sc Biotechnology","60"));
                ugList.add(new ProgramsData("Bachelor in Fine arts(Applied arts)","40"));
                ugList.add(new ProgramsData("Bachelor in Fashion design","20"));
                ugList.add(new ProgramsData("Bachelor of Interior Design","40"));
                ugList.add(new ProgramsData("Post Basic Nursing (PB BSc Nursing)","40"));
                ugList.add(new ProgramsData("BSc Cyber Security","120"));
                ugList.add(new ProgramsData("Information Technology","60"));
                ugList.add(new ProgramsData("BSC Data Science","60"));
                ugList.add(new ProgramsData("Master of Education","50"));
                ugList.add(new ProgramsData("Bachelor of Hotel Management & Catering Technology",""));
                ugList.add(new ProgramsData("First Year Engineering","480"));
                ugList.add(new ProgramsData("Electrical Engineering","60"));
                ugList.add(new ProgramsData("Automobile Engineering","120"));
                ugList.add(new ProgramsData("Civil Engineering","120"));
                ugList.add(new ProgramsData("Computer Engineering","60"));
                ugList.add(new ProgramsData("Mechanical Engineering","120"));
                ugList.add(new ProgramsData("Bachelor of Planning","40"));
                ugList.add(new ProgramsData("Bachelor of Architecture","80"));
                ugList.add(new ProgramsData("Bachelor of Pharmacy","60"));
                ugList.add(new ProgramsData("Bachelor of Commerce","120"));
                ugList.add(new ProgramsData("BSc Nursing","60"));
                ugList.add(new ProgramsData("Bachelor of Business Administrator Professional","500"));
                ugList.add(new ProgramsData("Integrated Master of Computer Applcation","60"));
                ugList.add(new ProgramsData("Bachelor of Social Work","60"));
                ugList.add(new ProgramsData("Bachelor of Arta","60"));
                ugList.add(new ProgramsData("Robotic and Automation Engineering","60"));
                ugList.add(new ProgramsData("Nano Technology","60"));
                ugList.add(new ProgramsData("Chemical Engineering","71"));
                ugList.add(new ProgramsData("Aeronautical Engineering","60"));
                ugList.add(new ProgramsData("Bachelor of Computer Application","180"));

                ugRecycler.setHasFixedSize(true);
                ugRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter = new ProgramsAdapter(getContext(),ugList);
                ugRecycler.setAdapter(adapter);
            }

        });

        pgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vv = (pgRecycler.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(pglayout, new AutoTransition());
                pgRecycler.setVisibility(vv);

                pgList = new ArrayList<>();

                pgList.add(new ProgramsData("M.Sc. Organic Chemistry","30"));
                pgList.add(new ProgramsData("Post Graduate Diploma In Medical Laboratory technology","60"));
                pgList.add(new ProgramsData("M.Sc. Medical Laboratory Technology","30"));
                pgList.add(new ProgramsData("M.Sc. microbiology","120"));
                pgList.add(new ProgramsData("M.Sc. Biotechnology","60"));
                pgList.add(new ProgramsData("Master of technology(Real Estate Valuation)","20"));
                pgList.add(new ProgramsData("Master in Planning","30"));
                pgList.add(new ProgramsData("Regulatory Affairs","15"));
                pgList.add(new ProgramsData("Pharmaceutics","18"));
                pgList.add(new ProgramsData("Pharmaceutics Quality Assurance","12"));
                pgList.add(new ProgramsData("Master of Commerce","60"));
                pgList.add(new ProgramsData("Master of Computer Application","240"));
                pgList.add(new ProgramsData("Master of Business And Admin","180"));
                pgList.add(new ProgramsData("Integrated Master of Business Administration","60"));
                pgList.add(new ProgramsData("Master of Social Work","60"));
                pgList.add(new ProgramsData("Master of Arts","60"));
                pgList.add(new ProgramsData("Information Technology","120"));
                pgList.add(new ProgramsData("M.Sc. Cyber Security","30"));
                pgList.add(new ProgramsData("Information Technology Integrated","120"));


                pgRecycler.setHasFixedSize(true);
                pgRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter = new ProgramsAdapter(getContext(),pgList);
                pgRecycler.setAdapter(adapter);
            }

        });

        diplomaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vv = (diplomaRecycler.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(diplomalayout, new AutoTransition());
                diplomaRecycler.setVisibility(vv);

                diplomaList = new ArrayList<>();

                diplomaList.add(new ProgramsData("Computer","300"));
                diplomaList.add(new ProgramsData("Mechanical Engineering","120"));
                diplomaList.add(new ProgramsData("Civil Engineering","240"));
                diplomaList.add(new ProgramsData("Electrical Engineering","90"));
                diplomaList.add(new ProgramsData("Automobile Engineering","180"));
                diplomaList.add(new ProgramsData("Electronics and Communication","60"));
                diplomaList.add(new ProgramsData("Information Technology","120"));
                diplomaList.add(new ProgramsData("Diploma In Architecture Assistantship","40"));
                diplomaList.add(new ProgramsData("Any Other","60"));
                diplomaList.add(new ProgramsData("Computer Engineering","60"));
                diplomaList.add(new ProgramsData("Diploma General Nursing and Midwifery","40"));
                diplomaList.add(new ProgramsData("Capital Goods","25"));
                diplomaList.add(new ProgramsData("Diploma in Pharmacy","60"));

                diplomaRecycler.setHasFixedSize(true);
                diplomaRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter = new ProgramsAdapter(getContext(),diplomaList);
                diplomaRecycler.setAdapter(adapter);
            }

        });

        phdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vv = (phdRecycler.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(phdlayout, new AutoTransition());
                phdRecycler.setVisibility(vv);

                phdList = new ArrayList<>();

                phdList.add(new ProgramsData("Mechanical Engineering PhD","75"));
                phdList.add(new ProgramsData("Civil Engineering PhD","75"));
                phdList.add(new ProgramsData("Electronics & Communication Engineering PhD","75"));
                phdList.add(new ProgramsData("Computer Application PhD","75"));
                phdList.add(new ProgramsData("Areonautical engineering PhD","75"));
                phdList.add(new ProgramsData("Pharmaceutical Science PhD","75"));
                phdList.add(new ProgramsData("Management PhD","75"));
                phdList.add(new ProgramsData("Commerce PhD","75"));
                phdList.add(new ProgramsData("Education Ph.D.","75"));
                phdList.add(new ProgramsData("Biotechnology Ph.D.","75"));
                phdList.add(new ProgramsData("Microbiology Ph.D.","75"));
                phdList.add(new ProgramsData("Botany Ph.D.","75"));
                phdList.add(new ProgramsData("Chemistry Ph.D.","75"));
                phdList.add(new ProgramsData("English PhD","75"));
                phdList.add(new ProgramsData("Planning Ph.D.","75"));

                phdRecycler.setHasFixedSize(true);
                phdRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter = new ProgramsAdapter(getContext(),phdList);
                phdRecycler.setAdapter(adapter);
            }

        });

        certificateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int vv = (certificateRecycler.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;
                TransitionManager.beginDelayedTransition(certificateLayout, new AutoTransition());
                certificateRecycler.setVisibility(vv);

                certificateList = new ArrayList<>();

                certificateList.add(new ProgramsData("Certificate Course in Vastu-Shastra","20"));
                certificateList.add(new ProgramsData("Interior Design- 1 Year Certificate Course","20"));
                certificateList.add(new ProgramsData("Bachelor of Interior Design","120"));
                certificateList.add(new ProgramsData("Automotive","25"));


                certificateRecycler.setHasFixedSize(true);
                certificateRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter = new ProgramsAdapter(getContext(),certificateList);
                certificateRecycler.setAdapter(adapter);
            }

        });


        return v;
    }
}