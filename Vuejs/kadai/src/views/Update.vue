<script setup>
import { useUsersStore } from '../stores/users';
import { useRouter } from 'vue-router';
import { onMounted, ref, reactive } from 'vue';

const router = useRouter();
const usersStore = useUsersStore();

const goToUsers = () => {
    router.push("/users");
};

const username = reactive({ val: '', isValid: 'true' });
const email = reactive({ val: '', isValid: 'true' });
const formIsValid = ref(true);
const base64textString = ref('');
const imageName = ref('');
const showImage = ref(false);

const validateForm = () => {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    formIsValid.value = true;
    if (username.val === "") {
        username.isValid = false;
        formIsValid.value = false;
    }

    if (!email.val.match(mailformat)) {
        email.isValid = false;
        formIsValid.value = false;
    }
}

const clearValidity = (input) => {
    if (input === 'username') {
        username.isValid = true;
    } else if (input === 'email') {
        email.isValid = true;
    }
}

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

onMounted(() => {
    username.val = usersStore.username;
    email.val = usersStore.email;
    // base64textString.value = usersStore.image;
});

const goToUpdateConfirm = () => {
    validateForm();
    if (!formIsValid.value) {
        alert("名前、メールをちゃんと入力して!!!");
        return;
    }

    usersStore.$patch({
        id: usersStore.id,
        username: username.val,
        email: email.val,
        image: base64textString.value
    })
    router.push("/update_confirm");

};
</script>

<template>
    <div class="update-h1">
        <h1>研修生 編集</h1>
        <hr>
    </div>
    <div class="input-container">
        <label for="idStyle">ID:</label>
        <p id="idStyle">{{ usersStore.id }}</p>
    </div>
    <div class="input-container">
        <label for="usernameStyle">名前:</label>
        <input type="text" id="usernameStyle" v-model.trim="username.val" @input="clearValidity('username')">
    </div>
    <p class="invalid-warning" v-if="!username.isValid">名前入れて!!!</p>
    <div class="input-container">
        <label for="emailStyle">メールアドレス:</label>
        <input type="text" id="emailStyle" v-model.trim="email.val" @input="clearValidity('email')">
    </div>
    <p class="invalid-warning" v-if="!email.isValid && email.val === ''">メール入れて!!!</p>
    <p class="invalid-warning" v-else-if="!email.isValid">このメールアドレスは使用不可です。</p>
    <div class="input-container">
        Old Image: <img :src="'../src/components/images/' + (usersStore.image || 'default.png')" alt=""
            class="roundPictures">
    </div>
    <div class="input-container">
        <input type="file" @change="convertToBase64" accept="image/*" />
        <br />
        <div v-if="showImage">
            <img :src="base64textString" :alt="imageName" class="roundPictures" />
            <p>New Image: {{ imageName }}</p>
        </div>
    </div>
    <div class="buttons">
        <button class="confirm-button" @click="goToUpdateConfirm">確認</button>
        <button class="cancel-button" @click="goToUsers">キャンセル</button>
    </div>
</template>

<style scoped>
.update-h1 {
    text-align: center;
}

.input-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
}

#idStyle {
    margin-left: 10px;
    margin-top: 0px;
    padding: 3px;
    width: 200px;
    height: 20px;
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

.roundPictures {
    border-radius: 50%;
    width: 50px;
    height: 50px;
}

.buttons {
    text-align: center;
    margin-top: 20px;
}

.cancel-button {
    margin-left: 25px;
}

.invalid-warning {
    text-align: center;
    color: red;
    font-size: 13px;
}
</style>