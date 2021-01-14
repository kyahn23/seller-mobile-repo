<template>
  <div class="q-pa-md">
    <div class="row">
      <div class="q-mt-xs text-subtitle1 text-weight-medium">
        <span class="text-blue-6">한국 텔레콤 홍길동</span>님 환영합니다!
      </div>
    </div>
    <p class="text-subtitle2 text-weight-medium q-my-xs">내 매장 상품 바로조회</p>
    <div class="row q-my-xs" style="height: 80px;">
      <div class="column col q-pr-sm">
        <q-btn
          push
          class="fit"
          color="light-blue-13"
          label="SKT 상품조회"
          to="/register"
        />
      </div>
      <div class="column col">
        <q-btn
          push
          class="fit"
          color="light-blue-12"
          label="KT 상품조회"
          to="/status"
        />
      </div>
      <div class="column col q-pl-sm">
        <q-btn
          push
          class="fit bg-red-2"
          color="bg-red-2"
          label="LGU 상품조회"
          to="/customer"
        />
      </div>
    </div>
    <p class="text-subtitle2 text-weight-medium q-my-sm">내 매장 상품 바로조회</p>
    <div class="row items-center justify-between">
      <q-select class="col-4"
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
    <p class="text-subtitle2 text-weight-medium q-my-sm">오늘 방문예정 고객</p>
    <div style="height: 40vh" class="row justify-center">
      <div class="self-center justify-center">아직 요청한 상담이 없습니다</div>
    </div>
    <div class="row justify-center text-">
      Copyright 영역
    </div>
    <!--    <q-list>-->
    <!--      <q-separator />-->
    <!--      <q-infinite-scroll @load="onScrollLoad" :offset="110">-->
    <!--        <div v-for="(dealOne, index) in dealList" :key="index">-->
    <!--          <q-item class="q-py-md">-->
    <!--            <q-item-section avatar>-->
    <!--              <q-avatar square size="4em">-->
    <!--                <img :src="$cf.imagePath(dealOne.pnImg)" />-->
    <!--              </q-avatar>-->
    <!--            </q-item-section>-->

    <!--            <q-item-section>-->
    <!--              <q-item-label-->
    <!--                class="text-caption text-weight-bold"-->
    <!--                style="font-size: 0.8em;"-->
    <!--              >-->
    <!--                {{ dealOne.pnMdlNm }}-->
    <!--              </q-item-label>-->
    <!--              <q-item-label class="text-caption">-->
    <!--                최저가-->
    <!--                <span class="text-h6 text-weight-bold">-->
    <!--                  <span class="text-primary">-->
    <!--                    {{-->
    <!--                      lowestPrice(dealOne.minPriceDv, dealOne.minPriceRt)-->
    <!--                        | showMeTheMoney-->
    <!--                    }}-->
    <!--                  </span>-->
    <!--                  <q-icon-->
    <!--                    name="delete_outline"-->
    <!--                    color="black"-->
    <!--                    @click="deleteClick(dealOne)"-->
    <!--                  />-->
    <!--                </span>-->
    <!--              </q-item-label>-->
    <!--              <q-item-label-->
    <!--                lines="2"-->
    <!--                class="text-caption"-->
    <!--                style="font-size: 0.6em;"-->
    <!--              >-->
    <!--                {{ dealOne.mntCarr }} / {{ dealOne.pnRegDis }} /-->
    <!--                {{ dealOne.pnMntRtNm }}-->
    <!--              </q-item-label>-->
    <!--            </q-item-section>-->

    <!--            <q-item-section side>-->
    <!--              <q-icon-->
    <!--                name="chevron_right"-->
    <!--                color="black"-->
    <!--                @click="dealClick(dealOne.dealNo)"-->
    <!--              />-->
    <!--            </q-item-section>-->
    <!--          </q-item>-->
    <!--          <q-separator />-->
    <!--        </div>-->
    <!--      </q-infinite-scroll>-->
    <!--      <q-item class="q-py-md" v-if="this.pageInfo.hasNextPage">-->
    <!--        <q-item-section>-->
    <!--          <q-spinner color="primary" size="sm" class="self-center" />-->
    <!--        </q-item-section>-->
    <!--      </q-item>-->
    <!--      <q-item-->
    <!--        clickable-->
    <!--        class="q-py-md"-->
    <!--        v-else-if="this.pageInfo.lastPage && this.pageInfo.totalPages > 1"-->
    <!--        @click="scrollTop"-->
    <!--      >-->
    <!--        <q-item-section>-->
    <!--          <q-item-label class="self-center">-->
    <!--            <q-icon name="expand_less" size="md" />-->
    <!--          </q-item-label>-->
    <!--          <q-item-label class="q-pb-md self-center text-caption">-->
    <!--            맨 위로 돌아가기-->
    <!--          </q-item-label>-->
    <!--        </q-item-section>-->
    <!--      </q-item>-->
    <!--      <q-item class="q-py-lg" v-if="this.pageInit && this.dealList.length < 1">-->
    <!--        <q-item-section class="text-grey-5">-->
    <!--          <q-item-label class="self-center">-->
    <!--            <q-icon name="error" size="xl" />-->
    <!--          </q-item-label>-->
    <!--          <q-item-label class="q-pt-sm self-center text-subtitle1">-->
    <!--            아직 요청한 상담이 없습니다-->
    <!--          </q-item-label>-->
    <!--        </q-item-section>-->
    <!--      </q-item>-->
    <!--    </q-list>-->
  </div>
</template>

<script>
export default {
  name: "PageMain",
  data() {
    return {
      kwd: "",
      selectOpt: "clientNm",
      optList: [
        {label: "고객명", value: "clientNm"},
        {label: "연락처", value: "clientNum"}
      ],
      dealList: [
        {mntCarr: "SKT", pnRegDis: "번호이동", pnMntRtNm: "요금제이름"}

      ]
    }
  }

};
</script>
