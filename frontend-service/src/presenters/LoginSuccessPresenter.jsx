import LoginSuccessView from "@/views/LoginSuccessView.jsx";

/**
 * Used to display a message when succesfully logging in.
 * @param {*} props The reactive model.
 * @returns 
 */
export default function LoginSuccessPresenter(props) {

    return <div class="main">
                <LoginSuccessView/>
           </div>;
}