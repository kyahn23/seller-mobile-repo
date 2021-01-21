<template>
  <div class="q-pt-md q-pb-xs">
    <div class="row q-px-md">
      <div class="q-mt-xs text-subtitle1 text-weight-medium">
        <span class="text-blue-6 q-mr-sm">{{ bnNm }}</span><span class="text-blue-6">{{ mbrNm }}</span>님 환영합니다!
      </div>
    </div>
    <p class="text-subtitle2 text-weight-medium q-my-xs q-px-md">내 매장 상품 바로조회</p>
    <div class="row q-my-xs q-px-md" style="height: 80px;">
      <div class="column col q-pr-sm">
        <q-btn
          push
          class="fit"
          color="light-blue-13"
          label="SKT 상품조회"
          to="/product/SKT"
        />
      </div>
      <div class="column col">
        <q-btn
          push
          class="fit"
          color="light-blue-12"
          label="KT 상품조회"
          to="/product/KT"
        />
      </div>
      <div class="column col q-pl-sm">
        <q-btn
          push
          class="fit bg-red-2"
          color="bg-red-2"
          label="LGU 상품조회"
          to="/product/LGU"
        />
      </div>
    </div>
    <p class="text-subtitle2 text-weight-medium q-my-sm q-px-md">내 매장 상담 바로조회</p>
    <div class="row items-center justify-between q-px-md">
      <q-select class="col-4"
                outlined
                dense
                v-model="selectOpt"
                :options="optList"
                emit-value
                map-options
      >
      </q-select>
      <div class="col-6">
        <q-input
          v-model="kwd"
          dense
          outlined
          class="q-px-sm"
        />
      </div>
      <div class="col-2">
        <q-btn unelevated
               padding="sm"
               color="primary"
               class="full-width"
               @click="csSearch">
          검색
        </q-btn>
      </div>
    </div>
    <p class="text-subtitle2 text-weight-medium q-my-sm q-px-md">오늘 방문예정 고객</p>
    <div style="min-height: 40vh">
<!--      <div class="self-center justify-center">아직 요청한 상담이 없습니다</div>-->
      <q-list>
        <q-infinite-scroll @load="onScrollLoad" :offset="110">
          <div v-for="(visit, idx) in visitList" :key="idx" style="border-bottom: 1px lightgrey solid">
            <q-item class="q-py-md">
              <q-item-section avatar>
                <q-avatar square size="5em">
                  <img :src="$cf.imagePath(visit.pnImg)">
                </q-avatar>
              </q-item-section>
              <q-item-section>
                <q-item-label class="text-caption text-weight-medium" style="font-size: 1em;">
                  <span class="q-mr-sm">{{visit.clMbrNm}}</span>
                  <!-- 상담상태에 따른 색 변경 필요 -->
                  <!--                <span v-if="visit.callStCd === 'R'" class="bg-deep-orange-3 rounded-borders text-white q-pa-xs">상담접수</span>-->
                  <span v-if="visit.callStCd === 'P'" class="bg-green-3 rounded-borders text-white q-pa-xs">방문예정</span>
                  <!--                <span v-else-if="visit.callStCd === 'T'" class="bg-blue-3 rounded-borders text-white q-pa-xs">개통완료</span>-->
                  <!--                <span v-else-if="visit.blkYn === 0 && (visit.callStCd === 'E'|| visit.callStCd === 'C')" class="bg-grey-6 rounded-borders text-white q-pa-xs">미개통</span>-->
                  <!--                <span v-else-if="visit.blkYn === 1 && (visit.callStCd === 'E'|| visit.callStCd === 'C')" class="bg-red-5 rounded-borders text-white q-pa-xs">블랙고객</span>-->
                </q-item-label>
                <q-item-label class="text-caption" style="font-size: 1em;">
                  {{ visit.pnMdlNm }} {{visit.pnStor}}GB
                </q-item-label>
                <q-item-label class="text-caption" style="font-size: 1em;">
                  {{ visit.mntCarr }} / {{visit.pnRegDis}} / {{visit.pnMntRtNm}}
                </q-item-label>
                <q-item-label class="text-caption" style="font-size: 1em;" v-if="visit.bnMbrNm">
                  등록자 : {{visit.bnMbrNm}}
                </q-item-label>
              </q-item-section>
              <q-item-section side>
                <q-icon
                  name="chevron_right"
                  color="black"
                  @click="csClick(visit, idx)"
                />
              </q-item-section>
            </q-item>
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
      <q-item class="q-py-lg" v-if="this.pageInit && this.visitList.length < 1">
        <q-item-section class="text-grey-5">
          <q-item-label class="self-center">
            <q-icon name="error" size="xl" />
          </q-item-label>
          <q-item-label class="q-pt-sm self-center text-subtitle1">
            오늘 방문예정인 고객이 없습니다.
          </q-item-label>
        </q-item-section>
      </q-item>
    </div>
    <div class="row justify-center q-mt-md">
      Copyright 영역
    </div>

  </div>
</template>

<script>
import { scroll } from "quasar";
const { getScrollTarget, setScrollPosition } = scroll;

export default {
  name: "PageMain",
  data() {
    return {
      bnMbrId: '',
      kwd: "",
      selectOpt: "clientNm",
      optList: [
        {label: "고객명", value: "clientNm"},
        {label: "연락처", value: "clientNum"}
      ],
      page: "1",
      pageInit: false,
      pageInfo: {},
      visitList: []
    }
  },
  mounted() {
    this.getInfo()
  },
  methods: {
    /** infinite scroll 이벤트 */
    onScrollLoad(index, done) {
      setTimeout(() => {
        if (index === 1) {
          this.getTodayVisit(index + "");
          done();
        } else if (this.pageInfo.hasNextPage) {
          this.getTodayVisit(this.pageInfo.nextPage + "");
          done();
        }
      }, 2000);
    },
    /** 맨 위로 돌아가기 이벤트 */
    scrollTop() {
      const ele = document.getElementById("top");
      const target = getScrollTarget(ele);
      const offset = ele.offsetTop - 1000;
      const duration = 200;
      setScrollPosition(target, offset, duration);
    },
    getInfo() {
      let param = {
        bnMbrId: this.$store.getters.currentUser
      }
      this.$cf.call(
        process.env.API + "/getinfo",
        param,
        this.getInfoCB,
        false
      )
    },
    getInfoCB(response) {
      this.$store.commit("setMbrName", {mbrName: response.mbrNm});
      this.$store.commit("setBnName", {bnName: response.bnName});
    },
    getTodayVisit(page) {
      let param = {
        searchDay : 'today',
        page: page
      }
      this.$cf.call(
        process.env.API + "/status/getVisitCsList",
        param,
        this.getTodayVisitCB,
        false
      )
    },
    getTodayVisitCB(response){
      this.pageInit = true
      this.pageInfo = response.pageInfo
      for (let n in response.counselList) {
        this.visitList.push(response.counselList[n])
      }


    },
    csSearch() {
      let param = {
        selectOpt: this.selectOpt,
        searchKwd: this.kwd
      }

      if (this.kwd === ''){
        this.$store.commit("setNotification", {
          color: "grey-8",
          textColor: "white",
          message: "검색어를 입력해주세요.",
        })
        return
      }
      this.$store.commit("setCsSearch", {mainCsSearch: param});
      this.$router.push({path: "/status"});
    },
    /** > 클릭 이벤트 */
    csClick(obj, idx) {
      this.$store.commit("setCs", {cs: obj});
      this.$router.push({path: "/status/detail/" + idx});
    },
  },
  computed: {
    mbrNm: {
      get() {
        return this.$store.getters.mbrName;
      }
    },
    bnNm: {
      get() {
        return this.$store.getters.bnName;
      }
    }
  }
};
</script>
