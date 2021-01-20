<template>
</template>

<script>
export default {
  name: 'Verify',
  mounted() {
    this.$store.commit("setLoading", {isLoading: true});

    let currentPath = window.location.href
    let loginStat = ''
    if (currentPath.indexOf('loginStat') !== -1) {
      loginStat = currentPath.substring(currentPath.lastIndexOf('=') + 1)
      if (loginStat.includes('SUCC')){
        let mbrId = currentPath.substring(currentPath.lastIndexOf('%3D') + 3)
        mbrId = mbrId.replace('%40', '@')

        this.$store.commit("setAuth", {isAuth: true});
        this.$store.commit("setCurrentUser", {currentUser: mbrId});

        this.$router.push({path: "/main"});
      }
    }
  }
}
</script>
