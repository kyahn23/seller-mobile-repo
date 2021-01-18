<template>
  <div>
    <div id="top" class="q-pt-md">
      <div class="q-px-md q-mb-xs">
        <span class="text-subtitle1 text-weight-bold">공지사항</span>
      </div>
      <q-separator />
      <div class="q-py-sm q-px-md row items-center justify-between">
        <q-select
          class="col-4"
          outlined
          dense
          v-model="selectOpt"
          :options="optList"
          emit-value
          map-options
        >
        </q-select>
        <div class="col-6">
          <q-input v-model="searchKwd" dense outlined class="q-px-sm" />
        </div>
        <div class="col-2">
          <q-btn unelevated padding="sm" color="primary" class="full-width" @click="initPage">
            검색
          </q-btn>
        </div>
      </div>
      <q-separator />
      <div class="q-px-md q-mb-xs row justify-end">
        <span class="q-py-sm text-right text-subtitle2">
          총 {{ pageInfo.totalCount ? pageInfo.totalCount : 0 }}건
        </span>
      </div>
    </div>

    <q-list>
      <q-separator />
      <div
        v-for="(board, index) in bnBrdList"
        :key="index"
        @click="boardClick(board.brdNo)"
      >
        <q-item>
          <q-item-section class="q-py-xs">
            <q-item-label class="text-subtitle2 text-weight-bold" lines="1">
              <q-btn
                v-if="board.brdImpYn === 'Y'"
                label="중요"
                size="xs"
                color="primary"
                padding="xs"
              />
              {{ board.brdTitle }}
            </q-item-label>
            <q-item-label caption>
              {{ board.brdRegDt }} | {{ board.brdWriterNm }}
            </q-item-label>
          </q-item-section>
          <q-item-section side>
            <q-item-label>
              <q-icon name="chevron_right" size="sm" />
            </q-item-label>
          </q-item-section>
        </q-item>
        <q-separator />
      </div>

      <q-item class="q-py-lg" v-if="this.pageInit && this.bnBrdList.length < 1">
        <q-item-section class="text-grey-5">
          <q-item-label class="self-center">
            <q-icon name="error" size="xl" />
          </q-item-label>
          <q-item-label class="q-pt-sm self-center text-subtitle1">
            해당하는 내용이 없습니다.
          </q-item-label>
        </q-item-section>
      </q-item>

      <q-item class="q-py-md" v-if="this.pageInfo.hasNextPage">
        <q-btn
          flat
          class="full-width text-weight-bold"
          color="primary"
          label="더 보기"
          icon-right="add"
          style="background-color: #E9F0F8;"
          @click="onBoardMore"
        />
      </q-item>

      <q-item
        clickable
        class="q-pt-none q-pb-sm"
        v-else-if="this.pageInfo.lastPage && this.pageInfo.totalPages > 1"
        @click="scrollTop"
      >
        <q-item-section>
          <q-item-label class="self-center">
            <q-icon name="expand_less" size="md" />
          </q-item-label>
          <q-item-label class="self-center text-caption">
            맨 위로 돌아가기
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>
  </div>
</template>

<script>
import { scroll } from "quasar";

const { getScrollTarget, setScrollPosition } = scroll;

export default {
  name: "PageBoard",
  data() {
    return {
      selectOpt: "boardTitle",
      optList: [
        { label: "제목", value: "boardTitle" },
        { label: "내용", value: "boardContent" }
      ],
      searchKwd: "",
      page: "1",
      /** 페이지 초기화 여부 */
      pageInit: false,
      /** 페이징 처리 정보 */
      pageInfo: {},
      /** 공지사항리스트 */
      bnBrdList: []
    };
  },
  mounted() {
    // this.$store.commit("setLoading", {isLoading: true});
    this.initPage()
  },
  methods: {
    /** 맨 위로 돌아가기 이벤트 */
    scrollTop() {
      const ele = document.getElementById("top");
      const target = getScrollTarget(ele);
      const offset = ele.offsetTop - 1000;
      const duration = 200;
      setScrollPosition(target, offset, duration);
    },
    /** 더 보기 버튼 클릭 이벤트 */
    onBoardMore() {
      this.getBoard(this.pageInfo.nextPage + "");
    },
    initPage(){
      if (this.page !== "1") this.page = "1"
      this.bnBrdList = []
      this.getBoard(this.page);
    },
    /** list 호출 함수 */
    getBoard(page) {
      this.$cf.call(
        process.env.API + "/shop/getBnBrdList",
        {
          bnMbrId : this.$store.getters.currentUser,
          selectOpt: this.selectOpt,
          searchKwd: this.searchKwd,
          page: page
        },
        this.getBoardCB,
        false
      );
      this.pageInit = true;
    },
    /** list 콜백 함수 */
    getBoardCB(response) {
      this.pageInfo = response.pageInfo;
      for (let n in response.bnBrdList) {
        this.bnBrdList.push(response.bnBrdList[n]);
      }
    },
    boardClick(no) {
      this.$router.push({ path: "/board/" + no });
    }
  }
};
</script>

<style scoped></style>
