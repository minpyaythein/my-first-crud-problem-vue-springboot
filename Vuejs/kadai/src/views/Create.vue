<script setup>
import { ref, reactive } from 'vue';
import { useUsersStore } from '../stores/users';
import { useRouter } from 'vue-router';

const usersStore = useUsersStore();
const router = useRouter();
const username = reactive({ val: '', isValid: true });
const email = reactive({ val: '', isValid: true });
const formIsValid = ref(true);
const base64textString = ref('');
const imageName = ref('');
const showImage = ref('false');

const convertToBase64 = (event) => {
    const file = event.target.files[0];
    imageName.value = file.name;
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
        base64textString.value = reader.result;
        showImage.value = true;
    };
    reader.onerror = (error) => {
        console.log('Error: ', error);
    };
}

const validateForm = () => {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    formIsValid.value = true;
    if (username.val === '') {
        username.isValid = false;
        formIsValid.value = false;
    }

    if (!email.val.match(mailformat)) {
        email.isValid = false;
        formIsValid.value = false;
    }
};

const clearValidity = (input) => {
    if (input === 'username') {
        username.isValid = true;
    } else if (input === 'email') {
        email.isValid = true;
    }
}

const goToCreateConfirm = () => {
    validateForm();
    if (!formIsValid.value) {
        alert("名前、メールをちゃんと入力して!!!");
        return;
    }
    // if (username.val.length === 0 || email.val.length === 0) {
    //     alert("Please check your username and email!!!");
    // } else {
    usersStore.$patch({
        username: username.val,
        email: email.val,
        image: base64textString
    });
    router.push("/create_confirm");
    //}
};

const goToUsers = () => {
    router.push("/users");
};
</script>

<template>
    <div class="create-h1">
        <h1>研修生 新規登録</h1>
    </div>
    <hr>
    <div class="input-container" :class="{ invalid: !username.isValid }">
        <label for="username">名前</label>
        <input type="text" id="username" class="username-input" v-model.trim="username.val"
            @input="clearValidity('username')">
    </div>
    <p class="invalid-warning" v-if="!username.isValid">名前入れて!!!</p>
    <div class="input-container" :class="{ invalid: !email.isValid }">
        <label for="email">メールアドレス</label>
        <input type="text" id="email" class="username-input" v-model.trim="email.val" @input="clearValidity('email')">
    </div>
    <p class="invalid-warning" v-if="!email.isValid && email.val === ''">メール入れて!!!</p>
    <p class="invalid-warning" v-else-if="!email.isValid">このメールアドレスは使用不可です。</p>
    <div class="input-container">
        <input type="file" @change="convertToBase64" accept="image/*" />
        <br />
        <div v-if="showImage">
            <img :src="base64textString" :alt="imageName" class="roundPictures" />
            <!-- <p>{{ imageName }}</p> -->
        </div>
        <br />
        <!-- <textarea v-model="base64textString" rows="5"></textarea> -->
    </div>
    <div class="buttons">
        <button class="confirm-button" @click="goToCreateConfirm">確認</button>
        <button class="cancel-button" @click="goToUsers">キャンセル</button>
    </div>
</template>

<style scoped>
.create-h1 {
    text-align: center;
}

.input-container {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.roundPictures {
    border-radius: 50%;
    width: 50px;
    height: 50px;
}

.username-input {
    margin-left: 10px;
}

.buttons {
    text-align: center;
    margin-top: 20px;
}

.cancel-button {
    margin-left: 25px;
}

.invalid label {
    color: red;
}

.invalid input,
.invalid textarea {
    border: 1px solid red;
}

.invalid-warning {
    text-align: center;
    color: red;
    font-size: 13px;
}
</style>