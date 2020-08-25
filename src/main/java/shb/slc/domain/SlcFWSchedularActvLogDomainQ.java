package shb.slc.domain;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SlcFWSchedularActvLogDomainQ {
    @NonNull private String schBatchDate;
    @NonNull private String schTargetService;
    private String schDescription;
}
