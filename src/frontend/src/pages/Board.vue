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
          map-options
        >
        </q-select>
        <div class="col-6">
          <q-input v-model="searchKwd" dense outlined class="q-px-sm" />
        </div>
        <div class="col-2">
          <q-btn unelevated padding="sm" color="primary" class="full-width">
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
        v-for="(board, index) in boardList"
        :key="index"
        @click="boardClick(board.brdId)"
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
              {{ board.inpDt }} | {{ board.brdWriter }}
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

      <q-item class="q-py-md" v-if="this.pageInit && this.pageInfo.hasNextPage">
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
      boardList: []
    };
  },
  mounted() {
    // this.$store.commit("setLoading", {isLoading: true});
    this.getBoard(this.page);
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
      // this.getBoard(this.pageInfo.nextPage + "");
    },
    /** list 호출 함수 */
    getBoard(page) {
      // this.$cf.call(
      //   process.env.API + "/api/customer/boardList",
      //   {
      //     page: page
      //   },
      //   this.getBoardCB,
      //   false
      // );
      this.pageInit = true;
      this.boardList = [
        {
          brdId: "1",
          brdImpYn: "Y",
          brdTitle: "중요한 공지 제목",
          inpDt: "2021-01-12",
          brdWriter: "홍길동"
        },
        {
          brdId: "2",
          brdImpYn: "N",
          brdTitle: "안 중요한 공지 제목",
          inpDt: "2021-01-11",
          brdWriter: "김철수"
        },
        {
          brdId: "3",
          brdImpYn: "N",
          brdTitle: "한 번에 불러올 때 총 10 ROW",
          inpDt: "2021-01-11",
          brdWriter: "김철수"
        },
        {
          brdId: "4",
          brdImpYn: "N",
          brdTitle: "지금은 치기 귀찮아서 4 ROW",
          inpDt: "2021-01-11",
          brdWriter: "김철수"
        }
      ];
      this.pageInfo = {
        hasNextPage: true,
        totalCount: 4
        // lastPage: true,
        // totalPages: 2
      };
    },
    /** list 콜백 함수 */
    getBoardCB(response) {
      // this.pageInfo = response.pageInfo;
      // for (let n in response.boardList) {
      //   this.boardList.push(response.boardList[n]);
      // }
    },
    boardClick(no) {
      this.$router.push({ path: "/board/" + no });
    }
  }
};
</script>

<style scoped></style>
