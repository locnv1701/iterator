package api.iterator.backend.controllers;

import api.iterator.backend.business.logic.SharerBusinessImpl;
import api.iterator.backend.exceptions.SystemException;
import api.iterator.backend.models.UserResponseModel;
import api.iterator.backend.models.displays.sharer.JobPostDisplayModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sharer")
public class SharerController {
    private final SharerBusinessImpl sharerBusinessImpl;

    public SharerController(SharerBusinessImpl sharerBusinessImpl) {
        this.sharerBusinessImpl = sharerBusinessImpl;
    }

    @PutMapping("/edit-job-post/{sharer_id}")
    public ResponseEntity<Object> editJobPost(
            @PathVariable("sharer_id") Long sharerId,
            @RequestParam(value = "job_post_id", required = false) Long jobPostId,
            @RequestParam("function") String function,
            @RequestBody(required = false) JobPostDisplayModel jobPostDisplayModel
    ) throws SystemException {
        Object data = sharerBusinessImpl.editJobPost(jobPostId, function, jobPostDisplayModel, sharerId);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
