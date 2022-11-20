import './PageNotFound.css'

const PageNotFound = () => {
    return (
        <div className='notfound-border'>
            <img src='/end1.gif' className='notfound-img'></img>
            <p className='notfound-text'>해당 페이지는 존재하지 않습니다 :(</p>
        </div>
    )
}

export default PageNotFound;