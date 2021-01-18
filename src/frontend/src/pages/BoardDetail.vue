<template>
  <div class="q-py-md">
    <q-list>
      <div>
        <q-item>
          <q-item-section>
            <q-item-label class="text-subtitle1 text-weight-bold" lines="1">
              <q-btn
                v-if="boardInfo.brdImpYn === 'Y'"
                unelevated
                label="중요"
                size="xs"
                color="primary"
                padding="xs"
              />
              {{ boardInfo.brdTitle }}
            </q-item-label>
            <q-item-label caption>
              {{ boardInfo.brdRegDt }} | {{ boardInfo.brdWriterNm }}
            </q-item-label>
          </q-item-section>
        </q-item>
        <q-separator/>
        <div class="row q-ma-sm q-px-sm full-width items-center" v-if="brdFileInfo.length > 0">
          <div class="q-px-xs text-caption text-weight-bold">첨부파일</div>
          <div class="column q-px-sm" v-if="brdFileInfo.length > 0">
            <div v-for="brdOneFile in brdFileInfo">
            <span class="fit"
                  style="border-bottom: 1px solid #000;"
                  @click="getFile(brdOneFile.storFilNm)"
            >
              {{ brdOneFile.origFilNm }}
            </span>
            </div>
          </div>
        </div>
        <q-separator/>
        <div class="q-ma-sm" style="min-height: 200px">
          <p class="full-width q-px-xs" style="word-break: keep-all;">
            {{ boardInfo.brdCont }}
          </p>
        </div>
        <q-separator/>
        <div class="q-mt-md full-width text-center">
          <q-btn unelevated color="primary" label="목록" @click="goToList"/>
        </div>
      </div>
    </q-list>
  </div>
</template>

<script>
import {scroll} from "quasar"

const {getScrollTarget, setScrollPosition} = scroll

export default {
  name: "PageBoardDetail",
  props: {
    brdNo: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      boardInfo: {},
      brdFileInfo: []
    }
  },
  mounted() {
    this.getBoardInfo()
  },
  methods: {
    goToList() {
      this.$router.push({path: "/board"})
    },
    getBoardInfo() {
      this.$cf.call(
        process.env.API + "/shop/getBnBrdOne",
        {
          brdNo: this.brdNo
        },
        this.getBoardInfoCB,
        true
      )
    },
    getBoardInfoCB(response) {
      this.boardInfo = response.bnBrdOne
      this.brdFileInfo = response.brdOneCurFileInfo
    },
    getFile(fileNm) {
      window.location.href = process.env.API + "/downloadFile?fileName=" + fileNm
    }
  }
}
</script>

<style scoped></style>
