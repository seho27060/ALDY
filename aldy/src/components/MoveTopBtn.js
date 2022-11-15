import './MoveTopBtn.css'
import { BiArrowToTop } from "react-icons/bi";

const MoveTopBtn = () => {
    return (
        <div className='move-to-top'>
            <BiArrowToTop onClick={() => {
                if (!window.scrollY) return;
                window.scrollTo({
                    top:0,
                    behavior: 'smooth'
                })
            }}></BiArrowToTop>
        </div>
    )
}

export default MoveTopBtn;