<template>
  <div class="q-pt-md">
    <div class="row">
      <div class="q-px-md text-subtitle1 text-weight-medium q-mb-xs">
        상담현황
      </div>
    </div>
    <q-separator/>
    <div class="row items-center justify-between q-px-md q-py-sm">
      <q-select class="col-4 bg-white"
                outlined
                dense
                v-model="selectOpt"
                :options="optList"
                emit-value
                map-options>
      </q-select>
      <div class="col-6">
        <q-input v-model="searchKwd" dense outlined class="q-px-sm"/>
      </div>
      <div class="col-2">
        <q-btn unelevated padding="sm" color="primary" class="full-width" label="검색" @click="searchBtn"/>
      </div>
    </div>
    <q-separator/>
    <div class="row q-py-sm">
      <span class="text-subtitle2 text-weight-medium q-px-md self-center">구분</span>
      <q-btn unelevated dense size="md" :color="myClient? 'primary':'white'"
             :flat="!myClient" :text-color="myClient? 'white':'black'"
             label="전체" class="q-mr-sm q-px-sm" @click="saleChg"/>
      <q-btn unelevated dense size="md" :color="!myClient? 'primary':'white'"
             :flat="myClient" :text-color="!myClient? 'white':'black'"
             label="내 담당고객" class="q-px-sm" @click="saleChg"/>
    </div>
    <q-separator/>
    <q-list>
      <q-infinite-scroll @load="onScrollLoad" :offset="110">
        <div v-for="(cs, idx) in viewList" :key="idx" style="border-bottom: 1px lightgrey solid">
          <q-item class="q-py-md">
            <q-item-section avatar>
              <q-avatar square size="5em">
                <img :src="$cf.imagePath(cs.pnImg)">
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-caption text-weight-medium" style="font-size: 1em;">
                <span class="q-mr-sm">{{cs.clMbrNm}}</span>
                <!-- 상담상태에 따른 색 변경 필요 -->
                <span v-if="cs.callStCd === 'R'" class="bg-deep-orange-3 rounded-borders text-white q-pa-xs">상담접수</span>
                <span v-else-if="cs.callStCd === 'P'" class="bg-green-3 rounded-borders text-white q-pa-xs">방문예정</span>
                <span v-else-if="cs.callStCd === 'T'" class="bg-blue-3 rounded-borders text-white q-pa-xs">개통완료</span>
                <span v-else-if="cs.blkYn === 0 && (cs.callStCd === 'E'|| cs.callStCd === 'C')" class="bg-grey-6 rounded-borders text-white q-pa-xs">미개통</span>
                <span v-else-if="cs.blkYn === 1 && (cs.callStCd === 'E'|| cs.callStCd === 'C')" class="bg-red-5 rounded-borders text-white q-pa-xs">블랙고객</span>
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 1em;">
                {{ cs.pnMdlNm }} {{cs.pnStor}}GB
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 1em;">
                {{ cs.mntCarr }} / {{cs.pnRegDis}} / {{cs.pnMntRtNm}}
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 1em;" v-if="cs.bnMbrNm">
                등록자 : {{cs.bnMbrNm}}
              </q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon
                name="chevron_right"
                color="black"
                @click="csClick(cs, idx)"
              />
            </q-item-section>
          </q-item>
<!--          <q-separator/>-->
        </div>
      </q-infinite-scroll>
      <q-item class="q-py-md" v-if="this.pageInfo.hasNextPage">
        <q-item-section>
          <q-spinner color="primary" size="sm" class="self-center"/>
        </q-item-section>
      </q-item>
      <q-item
        clickable
        class="q-py-md"
        v-else-if="this.pageInfo.lastPage && this.pageInfo.totalPages > 1"
        @click="scrollTop"
      >
        <q-item-section>
          <q-item-label class="self-center">
            <q-icon name="expand_less" size="md"/>
          </q-item-label>
          <q-item-label class="q-pb-md self-center text-caption">
            맨 위로 돌아가기
          </q-item-label>
        </q-item-section>
      </q-item>
    </q-list>

    <q-item class="q-py-lg" v-if="this.pageInit && this.counselList.length < 1">
      <q-item-section class="text-grey-5">
        <q-item-label class="self-center">
          <q-icon name="error" size="xl" />
        </q-item-label>
        <q-item-label class="q-pt-sm self-center text-subtitle1">
          해당하는 내용이 없습니다.
        </q-item-label>
      </q-item-section>
    </q-item>

  </div>
</template>

<script>
import { scroll } from "quasar";
const { getScrollTarget, setScrollPosition } = scroll;

export default {
  name: "Status",
  data() {
    return {
      selectOpt: "clientNm",
      optList: [
        {label: "고객명", value: "clientNm"},
        {label: "연락처", value: "clientNum"}
      ],
      myClient: true,
      searchKwd: "",
      page: "1",
      pageInit: false,
      pageInfo: {},
      counselList: [],
      myList:[],
      viewList: []
    }
  },
  mounted() {
    if (this.$store.getters.mainCsSearch.searchKwd){
      this.selectOpt = this.$store.getters.mainCsSearch.selectOpt
      this.searchKwd = this.$store.getters.mainCsSearch.searchKwd

      this.$store.commit("setCsSearch", {mainCsSearch: {}});
    }
  },
  watch:{
    myClient(value){
      if (value){
        this.viewList = this.counselList
      } else {
        this.viewList = this.myList
      }
    }
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
    /** infinite scroll 이벤트 */
    onScrollLoad(index, done) {
      setTimeout(() => {
        if (index === 1) {
          this.getList(index + "");
          done();
        } else if (this.pageInfo.hasNextPage) {
          this.getList(this.pageInfo.nextPage + "");
          done();
        }
      }, 2000);
    },
    searchBtn(){
      this.myClient = true
      this.counselList = []
      this.myList = []
      this.viewList = []
      this.getList(this.page)
    },
    getList(page) {
      this.$cf.call(
        process.env.API + "/status/getAllCounselList",
        {
          selectOpt: this.selectOpt,
          searchKwd: this.searchKwd,
          page: page
        },
        this.getListCB,
        false
      );
    },
    /** list 콜백 함수 */
    getListCB(response) {
      this.pageInit = true
      this.pageInfo = response.pageInfo;
      for (let n in response.counselList) {
        this.counselList.push(response.counselList[n]);
        if(this.counselList[n].bnMbrId === this.$store.getters.currentUser){
          this.myList.push(response.counselList[n])
        }
      }
      this.viewList = this.counselList // 검색시 구분을 전체로 초기화하기때문에 전체리스트를 선언
    },
    saleChg() {
      this.myClient = !this.myClient
    },
    /** > 클릭 이벤트 */
    csClick(obj, idx) {
      this.$store.commit("setCs", {cs: obj});
      this.$router.push({path: "/status/detail/" + idx});
    },
  }

};
</script>
