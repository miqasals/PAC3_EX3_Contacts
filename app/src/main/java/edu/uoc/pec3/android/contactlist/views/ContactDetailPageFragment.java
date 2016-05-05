package edu.uoc.pec3.android.contactlist.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uoc.pec3.android.contactlist.R;
import edu.uoc.pec3.android.contactlist.adapters.PersonalDataListAdapter;
import edu.uoc.pec3.android.contactlist.model.Contact;

/**
 * Created by Miquel Casals on 21/04/2016.
 *
 * Based on information and samples of the Android Developers web.
 * http://developer.android.com/intl/es/training/animation/screen-slide.html#viewpager
 *
 */
public class ContactDetailPageFragment extends Fragment {

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String LONG = "longitude";
    public static final String LAT = "latitude";
    public static final String DESCRIPTION  ="description";
    public static final String CONTACT_DATA = "contactData";


    public static final String ZOOM = "zoom=8";
    public static final String KEY = "key=AIzaSyCHsdIAUDE47DHt5pbmLoQkIY6OprOA5fE";
    public static final String TYPE = "maptype=terrain";


    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private Double longitude;
    private Double latitude;
    private String description;
    private ArrayList<String> contactData;

    // Empty constructor.
    public ContactDetailPageFragment() {
    }


    /**
     * Factory method for this fragment class. Constructs a new fragment for the
     * given page number.
     */
    public static ContactDetailPageFragment create(Contact contact, int pageNumber) {
        ContactDetailPageFragment fragment = new ContactDetailPageFragment();
        // Create a bundle for passing arguments to fragment.
        Bundle args = new Bundle();
        // Put the page number of the ViewPager
        args.putInt(ARG_PAGE, pageNumber);
        // Put the description of the contact.
        args.putString(DESCRIPTION, contact.getDescription());
        // Create a String ArrayList for save all contact data and put into bundle.
        ArrayList<String> contactData = new ArrayList<>();
        contactData.add(0,contact.getGender());
        contactData.add(1,contact.getBirthday());
        contactData.add(2,contact.getEmail());
        contactData.add(3,contact.getPhone());
        contactData.add(4,contact.getCity());
        contactData.add(5,contact.getCountry());
        contactData.add(6,contact.getUpdatedAt());
        contactData.add(7,contact.getCreatedAt());
        args.putStringArrayList(CONTACT_DATA, contactData);
        // Put the location parameters
        args.putDouble(LAT, contact.getLocation().getLatitude());
        args.putDouble(LONG, contact.getLocation().getLongitude());
        // Set bundle as argument of the fragment.
        fragment.setArguments(args);
        // Return the fragment with all necessary information.
        return fragment;
    }


    /**
     * In onCreate method initializes gloval variables for accessing them later. The
     * arguments are grouped by which page will we need them on ViewPager.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mPageNumber = args.getInt(ARG_PAGE);
        contactData = args.getStringArrayList(CONTACT_DATA);
        latitude = args.getDouble(LAT);
        longitude = args.getDouble(LONG);
        description = args.getString(DESCRIPTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView;
        /**
         * Depending the page number inflates the view that corresponds.
         */
        switch (mPageNumber){
            case 0:
                // Personal data
                rootView  = (ViewGroup) inflater
                        .inflate(R.layout.fragment_contact_detail_personal_data, container, false);
                ListView listView = (ListView) rootView.findViewById(R.id.contact_detail_personal_data_list);
                PersonalDataListAdapter adapter = new PersonalDataListAdapter(rootView.getContext(),
                        R.id.contact_detail_personal_data_list, contactData);
                listView.setAdapter(adapter);
                break;
            case 1:
                // Description
                rootView  = (ViewGroup) inflater
                        .inflate(R.layout.fragment_contact_detail_description, container, false);
                ((TextView) rootView.findViewById(R.id.contact_detail_description_text)).setText(description);
                break;
            case 2:
                // Location
                rootView  = (ViewGroup) inflater
                        .inflate(R.layout.fragment_contact_detail_location, container, false);
                ImageView map = (ImageView) rootView.findViewById(R.id.locationMap);

                String locationTxt = latitude + "," + longitude;
                String marker = "markers=color:red%7Clabel:S%7C" + locationTxt;

                String url = "https://maps.googleapis.com/maps/api/staticmap?center="
                        + locationTxt + "&" + ZOOM + "&" + "size=400x400&"
                        + TYPE + "&" + marker + "&" + KEY;

                Picasso.with(rootView.getContext()).load(url).fit().centerCrop().into(map);
                break;
            default:
                rootView  = (ViewGroup) inflater
                        .inflate(R.layout.fragment_contact_detail_personal_data, container, false);
                break;
        }

        return rootView;
    }
}
