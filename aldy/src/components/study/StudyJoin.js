import { useState, useEffect } from "react";

import { getStudyMember } from "../../api/study";
import StudyJoinItem from "./StudyJoinItem";

const StudyJoin = () => {
  const [studyMember, setStudyMember] = useState(null);

  useEffect(() => {
    // 서버에서 내게 요청온 목록 가져와서 list에 저장하기
    setStudyMember([
      {
        studyJoinId: "1",
        studyJoinName: "abcd123",
        description: "스터디 열심히 하겠습니다~",
      },
      {
        studyJoinId: "2",
        studyJoinName: "스터디 함께해요",
        description: "함께 공부하고 싶어요!!",
      },
      {
        studyJoinId: "3",
        studyJoinName: "aldyaldy111",
        description: "ALDY 화이팅!! ><",
      },
    ]);
  }, []);

  return (
    <div className="study-list-box">
      {studyMember?.map((item, studyJoinId) => (
        <StudyJoinItem key={studyJoinId} item={item} />
      ))}
    </div>
  );
};

export default StudyJoin;
