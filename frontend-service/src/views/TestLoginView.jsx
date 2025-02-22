import { ref, onMounted } from 'vue'
import { identificationService } from '../services/api'
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
    setup() {



        return {

        }
    },

    template: `
        <div>
            <div>
                <Form @submit="handleLogin" :validation-schema="schema">
                    <div>
                        <label for="username">Username</label>
                        <Field name="username" type="text"/>
                        <ErrorMessage name="username" />
                    </div>
                    <input></input>

                    <div>
                        <label for="password">Password</label>
                        <Field name="password" type="password" />
                        <ErrorMessage name="password"  />
                    </div>
                    <input></input>

                    <div>
                        <button>
                            <span
                                v-show="loading"
                            ></span>
                            <span>Login</span>
                        </button>
                    </div>

                    <div>
                        <div v-if="message" role="alert">
                            {{ message }}
                        </div>
                    </div>
                </Form>
            </div>
        </div>
  ` //end of template, must end with "`"
}