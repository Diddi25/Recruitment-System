/**
 * Is displayed if the user tries to access a forbidden page.
 * @param {*} props 
 * @returns A text message
 */
export default function ForbiddenPageView(props) {
    return (<div class="unavailable"> <h2>Sorry, this page is forbidden.</h2>
        </div>
        );
  }