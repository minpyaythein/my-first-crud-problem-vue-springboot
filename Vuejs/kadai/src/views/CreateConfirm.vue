<script setup>
import axios from 'axios';
import { useUsersStore } from '../stores/users';
import { useRouter } from 'vue-router';

const router = useRouter();
const usersStore = useUsersStore();

const goToUsers = () => {
    router.push("/users");
}

const createUsers = async () => {
    const newUser = {
        name: usersStore.username,
        email: usersStore.email,
        image: usersStore.image
    };

    try {
        const response = await axios.post("http://localhost:8080/users/create", newUser);
        console.log(response.data);
        router.push("/users");
    } catch (e) {
        console.error("Error:", e);
    }
};
</script>

<template>
    <div class="create-confirm-h1">
        <h1>研修生 新規登録 確認</h1>
        <hr>
    </div>
    <p class="confirm-question">下記の情報で新規登録をします。よろしいですか？</p>
    <div class="input-container">
        <label for="usernameStyle">名前:</label>
        <p id="usernameStyle">{{ usersStore.username }}</p>
    </div>
    <div class="input-container">
        <label for="emailStyle">メールアドレス:</label>
        <p id="emailStyle">{{ usersStore.email }}</p>
    </div>
    <div class="input-container">
        <label for="emailStyle">プロファイル:</label>
        <img :src="usersStore.image" alt="preview" class="roundPictures">
    </div>
    <div class="buttons">
        <button class="confirm-button" @click="createUsers">登録</button>
        <button class="cancel-button" @click="goToUsers">キャンセル</button>
    </div>
</template>

<style scoped>
.create-confirm-h1 {
    text-align: center;
}

.confirm-question {
    text-align: center;
}

.input-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
}

.roundPictures {
    border-radius: 50%;
    width: 50px;
    height: 50px;
}

#usernameStyle,
#emailStyle {
    margin-left: 10px;
    margin-top: 0px;
    border: 1px solid #ccc;
    padding: 3px;
    width: 200px;
    height: 20px;
}

.buttons {
    text-align: center;
    margin-top: 20px;
}

.cancel-button {
    margin-left: 25px;
}
</style>