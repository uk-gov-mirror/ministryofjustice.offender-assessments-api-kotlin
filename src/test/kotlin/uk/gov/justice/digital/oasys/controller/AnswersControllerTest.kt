package uk.gov.justice.digital.oasys.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.reactive.server.expectBody
import uk.gov.justice.digital.oasys.api.AssessmentAnswersDto

@SqlGroup(
  Sql(
    scripts = ["classpath:assessments/before-test-full.sql"],
    config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
  ),
  Sql(
    scripts = ["classpath:assessments/after-test-full.sql"],
    config = SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED),
    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
  )
)
@AutoConfigureWebTestClient(timeout = "36000")
class AnswersControllerTest : IntegrationTest() {

  private val assessmentId = 5433L

  @Test
  fun `valid oasys assessment Id returns list of answers`() {

    webTestClient.post().uri("/assessments/oasysSetId/$assessmentId/answers")
      .bodyValue(mapOf("10" to setOf("10.98"), "9" to setOf("9.99")))
      .headers(setAuthorisation(roles = listOf("ROLE_OASYS_READ_ONLY")))
      .exchange()
      .expectStatus().isOk
      .expectBody<AssessmentAnswersDto>()
      .consumeWith {
        val answers = it.responseBody
        assertThat(answers.assessmentId).isEqualTo(assessmentId)

        assertThat(answers.questionAnswers.map { a -> a.refQuestionCode })
          .containsExactlyInAnyOrderElementsOf(setOf("10.98", "9.99"))
        val answer = answers.questionAnswers.first { a -> a.refQuestionCode == "10.98" }
        assertThat(answer.displayOrder).isEqualTo(998)
        assertThat(answer.displayScore).isNull()
        assertThat(answer.oasysQuestionId).isEqualTo(1347931)
        assertThat(answer.questionText).isEqualTo("Issues of emotional wellbeing linked to risk of serious harm, risks to the individual and other risks")
        assertThat(answer.refQuestionId).isEqualTo(1762)

        val questionAnswers = answer.answers.toList()[0]
        assertThat(questionAnswers.displayOrder).isEqualTo(5)
        assertThat(questionAnswers.freeFormText).isNull()
        assertThat(questionAnswers.oasysAnswerId).isEqualTo(5343777L)
        assertThat(questionAnswers.ogpScore).isNull()
        assertThat(questionAnswers.ovpScore).isNull()
        assertThat(questionAnswers.qaRawScore).isNull()
        assertThat(questionAnswers.staticText).isEqualTo("Yes")
        assertThat(questionAnswers.refAnswerCode).isEqualTo("YES")
        assertThat(questionAnswers.refAnswerId).isEqualTo(1995)
      }
  }

  @Test
  fun `OasysSetPK with no questions returns emptyset`() {

    webTestClient.post().uri("/assessments/oasysSetId/12345/answers")
      .bodyValue(mapOf("10" to setOf("10.98"), "9" to setOf("9.99")))
      .headers(setAuthorisation(roles = listOf("ROLE_OASYS_READ_ONLY")))
      .exchange()
      .expectStatus().isOk
      .expectBody<AssessmentAnswersDto>()
      .consumeWith {
        val answers = it.responseBody
        assertThat(answers.assessmentId).isEqualTo(12345)
        assertThat(answers.questionAnswers).isEmpty()
      }
  }
}
