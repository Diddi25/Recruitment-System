import LoginView from "@/views/LoginView";
import LoginSuccessView from "@/views/LoginSuccessView";
import { useStore } from "vuex";

export default function LoginPresenter(props) {
    const store = useStore();
    return <div class="main">
                <LoginView model={props.model}
                           store={store}
           /></div>;
}