<template>
  <div class="q-pt-sm">
    <div class="row">
      <div class="q-px-md text-subtitle1 text-weight-medium">
        상담현황
      </div>
    </div>
    <div class="row items-center justify-between q-px-md q-py-sm">
      <q-select class="col-4 bg-white"
                outlined
                dense
                v-model="selectOpt"
                :options="optList"
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
               class="full-width">
          검색
        </q-btn>
      </div>
    </div>
    <q-separator/>
    <div class="row q-py-sm">
      <span class="text-subtitle2 text-weight-medium q-px-md self-center">구분</span>
      <q-btn unelevated dense size="md" :color="sales? 'primary':'white'"
             :flat="!sales" :text-color="sales? 'white':'black'"
             label="전체" class="q-mr-sm q-px-sm" @click="saleChg"/>
      <q-btn unelevated dense size="md" :color="!sales? 'primary':'white'"
             :flat="sales" :text-color="!sales? 'white':'black'"
             label="내 담당고객" class="q-px-sm" @click="saleChg"/>
    </div>
    <q-separator/>
    <q-list>
      <q-infinite-scroll @load="onScrollLoad" :offset="110">
        <div v-for="(deal, idx) in dealList" :key="idx">
          <q-item class="q-py-md">
            <q-item-section avatar>
              <q-avatar square size="4em">
                <img :src="'images/phone_search.png'">
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-caption text-weight-medium" style="font-size: 0.8em;">
                <span class="q-mr-sm">손흥민</span><span class="bg-green-3 rounded-borders text-white q-pa-xs">방문예정</span>
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                갤럭시 S21 128GB
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                SKT / 번호이동 / 5GX 플래티넘
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                등록자 : 직원1
              </q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon
                name="chevron_right"
                color="black"
                @click="dealClick(1)"
              />
            </q-item-section>
          </q-item>
          <q-separator/>
          <q-item class="q-py-md">
            <q-item-section avatar>
              <q-avatar square size="4em">
                <img :src="'images/phone_search.png'">
              </q-avatar>
            </q-item-section>
            <q-item-section>
              <q-item-label class="text-caption text-weight-medium" style="font-size: 0.8em;">
                <span class="q-mr-sm">손흥민</span><span class="bg-green-3 rounded-borders text-white q-pa-xs">방문예정</span>
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                갤럭시 S21 128GB
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                SKT / 번호이동 / 5GX 플래티넘
              </q-item-label>
              <q-item-label class="text-caption" style="font-size: 0.8em;">
                등록자 : 직원1
              </q-item-label>
            </q-item-section>
            <q-item-section side>
              <q-icon
                name="chevron_right"
                color="black"
                @click="dealClick(1)"
              />
            </q-item-section>
          </q-item>
          <q-separator/>
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


    <div style="height: 40vh" class="row justify-center">
      <div class="self-center justify-center">해당하는 내용이 없습니다</div>
    </div>

  </div>
</template>

<script>
export default {
  name: "Status",
  data() {
    return {
      pageInit: false,
      pageInfo: {},
      kwd: "",
      selectOpt: "clientNm",
      optList: [
        {label: "고객명", value: "clientNm"},
        {label: "연락처", value: "clientNum"}
      ],
      dealList: [
        {mntCarr: "SKT", pnRegDis: "번호이동", pnMntRtNm: "요금제이름"}
      ],
      sales: true
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
    getList(page) {
      // this.$cf.call(
      //   process.env.API + "/api/deal/list",
      //   {
      //     email: this.currentUser,
      //     page: page
      //   },
      //   this.getListCB,
      //   true
      // );
    },
    /** list 콜백 함수 */
    getListCB(response) {
      // this.pageInfo = response.pageInfo;
      // for (let n in response.dealList) {
      //   this.dealList.push(response.dealList[n]);
      // }
      // this.pageInit = true;
    },
    saleChg() {
      this.sales = !this.sales
    },
    /** > 클릭 이벤트 */
    dealClick(dealNo) {
      this.$router.push({ path: "/status/detail/" + dealNo });
    },
  }

};
</script>
