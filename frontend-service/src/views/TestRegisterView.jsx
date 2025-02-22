import { ref, onMounted } from "vue";
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
  },

  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      email: yup
        .string()
        .required("Email is required!")
        .email("Email is invalid!")
        .max(50, "Must be maximum 50 characters!"),
      password: yup
        .string()
        .required("Password is required!")
        .min(6, "Must be at least 6 characters!")
        .max(40, "Must be maximum 40 characters!"),
    });

    return {
      loading: false,
      message: "",
      successful: false,
      schema,
    };
  },

  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },

  mounted() {
    if (this.loggedIn) {
      this.$router.push("/apply");
    }
  },

  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;

      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
        }
      );
    },
  },

  template: `
    <div>
      <div>
        <Form @submit="handleRegister" :validation-schema="schema">
          <div v-if="!successful">
            <div>
              <label for="username">Username</label>
              <Field name="username" type="text" />
              <ErrorMessage name="username" />
            </div>
            <div>
              <label for="email">Email</label>
              <Field name="email" type="email" />
              <ErrorMessage name="email" />
            </div>
            <div>
              <label for="password">Password</label>
              <Field name="password" type="password" />
              <ErrorMessage name="password" />
            </div>
            <button :disabled="loading">
              <span v-show="loading"></span>
              Sign Up
            </button>
          </div>
        </Form>
        <div v-if="message" :class="successful ? 'success' : 'error'">
          {{ message }}
        </div>
      </div>
    </div>
  ` // End of template, must use "`"
};
