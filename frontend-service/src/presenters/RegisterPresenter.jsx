/*
* Is used to register a user.
*/

import RegisterView from "@/views/RegisterView";
import { useStore } from "vuex";

export default function RegisterPresenter(props) {
    const store = useStore();
    return <div class="main">
                <RegisterView store={store} model={props.model}

           /></div>;
}