import java.util.Date;

public class PlusTest {


//    EntityWrapper<Pipeline> wrapper = new EntityWrapper<>();
//    String pipelineName = conditaion.getString("pipelineName");
//    Long projectId = conditaion.getLong("projectId");
//    String status = conditaion.getString("status");
//    Date beginPlanedStartTime = conditaion.getDate("beginPlanedStartTime");
//    Date endPlanedStartTime = conditaion.getDate("endPlanedStartTime");
//    Date beginActuralStartTime = conditaion.getDate("beginActuralStartTime");
//    Date endActuralStartTime = conditaion.getDate("endActuralStartTime");
//    String ownerId = conditaion.getString("ownerId");
//    Boolean involvedPipeline = conditaion.getBoolean("involvedPipeline");
//    Boolean unclosedPipeline = conditaion.getBoolean("unclosedPipeline");
//
//        wrapper.like(StringUtils.isNoneEmpty(pipelineName), "pipelineName", pipelineName);
//        wrapper.eq("deleted", false);
//        wrapper.eq(projectId != null && projectId != 0, "projectId", projectId);
//        if (unclosedPipeline != null && unclosedPipeline){
//        wrapper.notIn("status", "关闭", "暂停", "取消");
//    } else {
//        wrapper.eq(StringUtils.isNoneEmpty(status), "status", status);
//    }
//        wrapper.between(beginPlanedStartTime != null && endPlanedStartTime != null, "planedStartTime", beginPlanedStartTime, endPlanedStartTime);
//        wrapper.between(beginActuralStartTime != null && endActuralStartTime != null, "acturalStartTime", beginActuralStartTime, endActuralStartTime);
//        if (involvedPipeline != null && involvedPipeline) {
//        SysUser curSysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
//        wrapper.eq("ownerId", curSysUser.getUid());
//    } else {
//        wrapper.eq(StringUtils.isNoneEmpty(ownerId), "ownerId", ownerId);
//    }
}
