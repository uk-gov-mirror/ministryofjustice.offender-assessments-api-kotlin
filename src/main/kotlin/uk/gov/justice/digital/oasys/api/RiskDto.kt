package uk.gov.justice.digital.oasys.api

import io.swagger.annotations.ApiModelProperty
import uk.gov.justice.digital.oasys.jpa.entities.Assessment
import java.time.LocalDateTime

data class RiskDto(

  @ApiModelProperty(value = "Oasys Set ID/ Assessment ID", example = "1234")
  val oasysSetId: Long? = null,

  @ApiModelProperty(value = "Assessment Reference Version Code", example = "Layer3")
  val refAssessmentVersionCode: String? = null,

  @ApiModelProperty(value = "Assessment Reference Version", example = "1")
  val refAssessmentVersionNumber: String? = null,

  @ApiModelProperty(value = "Assessment Reference Version", example = "1")
  val refAssessmentId: Long ? = null,

  @ApiModelProperty(value = "Completed Date", example = "2020-01-02T16:00:00")
  val completedDate: LocalDateTime? = null,

  @ApiModelProperty(value = "Voided Date", example = "2020-01-02T16:00:00")
  val voidedDateTime: LocalDateTime? = null,

  @ApiModelProperty(value = "Assessment Completed True/False", example = "false")
  val assessmentCompleted: Boolean? = null,

  @ApiModelProperty(value = "Assessment Status", example = "OPEN")
  val assessmentStatus: String? = null,

  @ApiModelProperty(value = "SARA risk answers")
  val sara: RiskAssessmentAnswersDto? = null,

  @ApiModelProperty(value = "ROSHA risk answers")
  val rosha: RiskAssessmentAnswersDto? = null,
) {
  companion object {

    fun fromSara(assessment: Assessment?, answers: AssessmentAnswersDto): RiskDto {
      return RiskDto(
        oasysSetId = assessment?.oasysSetPk,
        refAssessmentVersionCode = assessment?.assessmentVersion?.refAssVersionCode,
        refAssessmentVersionNumber = assessment?.assessmentVersion?.versionNumber,
        refAssessmentId = assessment?.assessmentVersion?.refAssVersionUk,
        completedDate = assessment?.dateCompleted,
        voidedDateTime = assessment?.assessmentVoidedDate,
        assessmentCompleted = assessment?.dateCompleted != null,
        assessmentStatus = assessment?.assessmentStatus,
        sara = RiskAssessmentAnswersDto.fromSara(answers)
      )
    }

    fun fromRosha(assessment: Assessment?, answers: AssessmentAnswersDto): RiskDto {
      return RiskDto(
        oasysSetId = assessment?.oasysSetPk,
        refAssessmentVersionCode = assessment?.assessmentVersion?.refAssVersionCode,
        refAssessmentVersionNumber = assessment?.assessmentVersion?.versionNumber,
        refAssessmentId = assessment?.assessmentVersion?.refAssVersionUk,
        completedDate = assessment?.dateCompleted,
        voidedDateTime = assessment?.assessmentVoidedDate,
        assessmentCompleted = assessment?.dateCompleted != null,
        assessmentStatus = assessment?.assessmentStatus,
        rosha = RiskAssessmentAnswersDto.fromRosha(answers)
      )
    }

    fun fromRoshaWithSara(assessment: Assessment?, roshaAnswers: AssessmentAnswersDto, saraAnswers: AssessmentAnswersDto): RiskDto {
      return RiskDto(
        oasysSetId = assessment?.oasysSetPk,
        refAssessmentVersionCode = assessment?.assessmentVersion?.refAssVersionCode,
        refAssessmentVersionNumber = assessment?.assessmentVersion?.versionNumber,
        refAssessmentId = assessment?.assessmentVersion?.refAssVersionUk,
        completedDate = assessment?.dateCompleted,
        voidedDateTime = assessment?.assessmentVoidedDate,
        assessmentCompleted = assessment?.dateCompleted != null,
        assessmentStatus = assessment?.assessmentStatus,
        sara = RiskAssessmentAnswersDto.fromSara(saraAnswers),
        rosha = RiskAssessmentAnswersDto.fromRosha(roshaAnswers)
      )
    }
  }
}
