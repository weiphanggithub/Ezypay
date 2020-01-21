<template>
  <div id="app">
    <Subscription msg="Create a subscription"/>
  </div>
</template>

<script>
import Subscription from './components/Subscription.vue'

export default {
  name: 'app',
  components: {
    Subscription
  }
}

</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
