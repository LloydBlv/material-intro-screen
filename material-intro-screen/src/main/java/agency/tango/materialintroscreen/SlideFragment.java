package agency.tango.materialintroscreen;

import agency.tango.materialintroscreen.parallax.ParallaxFragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlideFragment extends ParallaxFragment {
  private final static String BACKGROUND_COLOR = "background_color";
  private final static String BACKGROUND_COLOR_RES = "background_color_res";
  private static final String BUTTONS_COLOR = "buttons_color";
  private static final String BUTTONS_COLOR_RES = "buttons_color_res";
  private static final String TITLE = "title";
  private static final String DESCRIPTION = "description";
  private static final String NEEDED_PERMISSIONS = "needed_permission";
  private static final String POSSIBLE_PERMISSIONS = "possible_permission";
  private static final String IMAGE_RES = "image_res";
  private static final String IMAGE_URL = "image_url";
  private static final int PERMISSIONS_REQUEST_CODE = 15621;

  private int backgroundColorResId;
  private int backgroundColor;

  private int buttonsColor;
  private int buttonsColorResId;

  private int imageResId;
  private String imageUrl;

  private String title;
  private String description;
  private String[] neededPermissions;
  private String[] possiblePermissions;

  private TextView titleTextView;
  private TextView descriptionTextView;
  private ImageView imageView;

  public static SlideFragment createInstance(SlideFragmentBuilder builder) {
    SlideFragment slideFragment = new SlideFragment();

    Bundle bundle = new Bundle();

    bundle.putInt(BACKGROUND_COLOR, builder.backgroundColor);
    bundle.putInt(BACKGROUND_COLOR_RES, builder.backgroundColorResId);

    bundle.putInt(BUTTONS_COLOR, builder.buttonsColor);
    bundle.putInt(BUTTONS_COLOR_RES, builder.buttonsColorResId);

    bundle.putInt(IMAGE_RES, builder.imageResId);
    bundle.putString(IMAGE_URL, builder.imageUrl);

    bundle.putString(TITLE, builder.title);
    bundle.putString(DESCRIPTION, builder.description);
    bundle.putStringArray(NEEDED_PERMISSIONS, builder.neededPermissions);
    bundle.putStringArray(POSSIBLE_PERMISSIONS, builder.possiblePermissions);

    slideFragment.setArguments(bundle);
    return slideFragment;
  }

  public static boolean isNotNullOrEmpty(String string) {
    return string != null && !string.isEmpty();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_slide, container, false);
    titleTextView = (TextView) view.findViewById(R.id.txt_title_slide);
    descriptionTextView = (TextView) view.findViewById(R.id.txt_description_slide);
    imageView = (ImageView) view.findViewById(R.id.image_slide);
    initializeView();

    titleTextView.setTextColor(buttonsColor());
    descriptionTextView.setTextColor(buttonsColor());
    return view;
  }

  public void initializeView() {
    Bundle bundle = getArguments();

    backgroundColorResId = bundle.getInt(BACKGROUND_COLOR_RES);
    buttonsColorResId = bundle.getInt(BUTTONS_COLOR_RES);

    backgroundColor = bundle.getInt(BACKGROUND_COLOR);
    buttonsColor = bundle.getInt(BUTTONS_COLOR);

    imageResId = bundle.getInt(IMAGE_RES, 0);
    imageUrl = bundle.getString(IMAGE_URL);

    title = bundle.getString(TITLE);
    description = bundle.getString(DESCRIPTION);
    neededPermissions = bundle.getStringArray(NEEDED_PERMISSIONS);
    possiblePermissions = bundle.getStringArray(POSSIBLE_PERMISSIONS);

    updateViewWithValues();
  }

  public int backgroundColor() {
    if (backgroundColorResId != 0) {
      return ContextCompat.getColor(getActivity(), backgroundColorResId);
    } else {
      return backgroundColor;
    }
  }

  public int buttonsColor() {
    if (buttonsColorResId != 0) {
      return ContextCompat.getColor(getActivity(), buttonsColorResId);
    } else {
      return buttonsColor;
    }
  }

  public boolean hasAnyPermissionsToGrant() {
    boolean hasPermissionToGrant = hasPermissionsToGrant(neededPermissions);
    if (!hasPermissionToGrant) {
      hasPermissionToGrant = hasPermissionsToGrant(possiblePermissions);
    }
    return hasPermissionToGrant;
  }

  public boolean hasNeededPermissionsToGrant() {
    return hasPermissionsToGrant(neededPermissions);
  }

  public boolean canMoveFurther() {
    return true;
  }

  public String cantMoveFurtherErrorMessage() {
    return getString(R.string.impassable_slide);
  }

  private void updateViewWithValues() {
    titleTextView.setText(title);
    descriptionTextView.setText(description);

    if (imageResId != 0) {
      imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), imageResId));
      imageView.setVisibility(View.VISIBLE);
    } else if (!TextUtils.isEmpty(imageUrl)) {
      Glide.with(this).load(imageUrl)
          //.apply(RequestOptions.centerCropTransform().error(new ColorDrawable(Color.RED)).placeholder(new ColorDrawable(Color.GRAY)))
          .into(imageView);
    }
  }

  public void askForPermissions() {
    ArrayList<String> notGrantedPermissions = new ArrayList<>();

    if (neededPermissions != null) {
      for (String permission : neededPermissions) {
        if (isNotNullOrEmpty(permission)) {
          if (ContextCompat.checkSelfPermission(getContext(), permission)
              != PackageManager.PERMISSION_GRANTED) {
            notGrantedPermissions.add(permission);
          }
        }
      }
    }
    if (possiblePermissions != null) {
      for (String permission : possiblePermissions) {
        if (isNotNullOrEmpty(permission)) {
          if (ContextCompat.checkSelfPermission(getContext(), permission)
              != PackageManager.PERMISSION_GRANTED) {
            notGrantedPermissions.add(permission);
          }
        }
      }
    }

    String[] permissionsToGrant = removeEmptyAndNullStrings(notGrantedPermissions);
    ActivityCompat.requestPermissions(getActivity(), permissionsToGrant, PERMISSIONS_REQUEST_CODE);
  }

  private boolean hasPermissionsToGrant(String[] permissions) {
    if (permissions != null) {
      for (String permission : permissions) {
        if (isNotNullOrEmpty(permission)) {
          if (ContextCompat.checkSelfPermission(getContext(), permission)
              != PackageManager.PERMISSION_GRANTED) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  private String[] removeEmptyAndNullStrings(final ArrayList<String> permissions) {
    List<String> list = new ArrayList<>(permissions);
    list.removeAll(Collections.singleton(null));
    return list.toArray(new String[list.size()]);
  }
}
