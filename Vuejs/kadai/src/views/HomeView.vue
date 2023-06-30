<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToUsers = () => {
  router.push("/users");
}
</script>

<template>
  <main>

  </main>

  <body>
    <h1 id="trainees-h1">研修生コミュニティー</h1>
    <div class="container">
      <button @click="goToUsers">一覧</button>
    </div>
  </body>
</template>

<style scoped>
#trainees-h1 {
  text-align: center;
  font-size: 60px;
}

div .container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 5vh;
  margin: 0;
}
</style>
