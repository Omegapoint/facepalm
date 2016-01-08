package se.omegapoint.facepalm.application;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;
import se.omegapoint.facepalm.domain.Commercial;

import java.util.List;

@Service
public class CommercialService {

    public List<Commercial> getCommercials() {
        return ImmutableList.of(new Commercial("Red dead redemption", "images/commercials/reddead.jpg"));
    }
}
