export default function MainPost(props) {
    return (
        <div className="row">
            <div className="col-md-3 text-left" style={{marginTop: "-12px"}}>
                <button className="main-button" style={{margin: "10"}} onClick={props.main}> Main </button>
                <button className="post-button" style={{margin: "10px"}} onClick={props.post}> Post </button>
            </div>
        </div>
    );
}