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
              {{ boardInfo.inpDt }} | {{ boardInfo.brdWriter }}
            </q-item-label>
          </q-item-section>
        </q-item>
        <q-separator />
        <div class="row q-ma-sm q-px-sm full-width items-center">
          <div class="q-px-xs text-caption text-weight-bold">첨부파일</div>
          <div class="column q-px-sm">
            <div
              v-if="boardInfo.brdAttFile1"
              style="border-bottom: 1px solid #000;"
              @click="getFile(boardInfo.brdAttFile1)"
            >
              {{ boardInfo.brdAttFile1 }}
            </div>
            <div
              v-if="boardInfo.brdAttFile2"
              style="border-bottom: 1px solid #000;"
              @click="getFile(boardInfo.brdAttFile2)"
            >
              {{ boardInfo.brdAttFile2 }}
            </div>
          </div>
        </div>
        <q-separator />
        <div class="q-ma-sm" style="min-height: 200px">
          <p class="full-width q-px-xs" style="word-break: keep-all;">
            {{ boardInfo.brdContent }}
          </p>
        </div>
        <q-separator />
        <div class="q-mt-md full-width text-center">
          <q-btn unelevated color="primary" label="목록" @click="goToList" />
        </div>
      </div>
    </q-list>
  </div>
</template>

<script>
import { scroll } from "quasar";

const { getScrollTarget, setScrollPosition } = scroll;

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
      boardInfo: {}
    };
  },
  mounted() {
    this.getBoardInfo();
  },
  methods: {
    goToList() {
      // this.$router.go(-1);
      this.$router.push({ path: "/board" });
    },
    getBoardInfo() {
      //   this.$cf.call(
      //     process.env.API + "/api/customer/boardDetail",
      //     {
      //       brdNo: this.brdNo
      //     },
      //     this.getBoardInfoCB,
      //     true
      //   );
      this.boardInfo = {
        brdId: "1",
        brdImpYn: "Y",
        brdTitle: "중요한 공지 제목",
        brdContent: "중요한 공지 내용",
        inpDt: "2021-01-12",
        brdWriter: "홍길동",
        brdAttFile1: "파일1.jpg",
        brdAttFile2: "파일2.pdf"
      };
    },
    getBoardInfoCB(response) {
      //   this.boardInfo = response;
    },
    getFile(fileNm) {}
  }
};
</script>

<style scoped></style>
