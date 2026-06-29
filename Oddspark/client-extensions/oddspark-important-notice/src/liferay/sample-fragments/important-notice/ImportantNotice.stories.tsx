import type { Meta, StoryObj } from '@storybook/react';
import { fn } from '@storybook/test';
import ImportantNotice from './index';
import type { Notice } from './useImportantNotice';

const MOCK_NOTICES: Notice[] = [
    {
        id: 1001,
        noticeTitle: '【重要】システムメンテナンスのお知らせ',
        noticeContent: '2026年6月30日（火）00:00〜06:00の間、システムメンテナンスを実施します。',
        importantNoticeStart: '2026-06-01 00:00:00',
        importantNoticeEnd: '2026-06-30 23:59:59',
        displayBeforeOrAfterLogin: 'both',
    },
    {
        id: 1002,
        noticeTitle: '【緊急】競馬レース払い戻し金の訂正について',
        noticeContent: '6月28日の競馬レース結果の払い戻し金に誤りがありました。修正対応中です。',
        importantNoticeStart: '2026-06-28 12:00:00',
        importantNoticeEnd: null,
        displayBeforeOrAfterLogin: 'both',
    },
    {
        id: 1003,
        noticeTitle: '【重要】アプリ新バージョンリリースのお知らせ',
        noticeContent: 'アプリVer3.0.0をリリースしました。最新版へのアップデートをお願いします。',
        importantNoticeStart: '2026-06-25 09:00:00',
        importantNoticeEnd: '2026-07-10 23:59:59',
        displayBeforeOrAfterLogin: 'after',
    },
];

const meta: Meta<typeof ImportantNotice> = {
    title: 'Components/ImportantNotice',
    component: ImportantNotice,
    args: {
        onNoticeClick: fn(),
        label: '重要',
        labelColor: '#FFD447',
        titleColor: '#FFFFFF',
        contentColor: '#FFFFFF',
        bgColor: '#3A403E',
    },
    argTypes: {
        label: { control: 'text' },
        labelColor: { control: 'color' },
        titleColor: { control: 'color' },
        contentColor: { control: 'color' },
        bgColor: { control: 'color' },
    },
};

export default meta;
type Story = StoryObj<typeof ImportantNotice>;

/** 通常表示 — 1件のみ。本文を表示する。 */
export const SingleNotice: Story = {
    args: { notices: [MOCK_NOTICES[0]] },
};

/** 2件以上 — カルーセル表示。本文は非表示。 */
export const MultipleNotices: Story = {
    args: { notices: MOCK_NOTICES },
};

/** 0件 — コンポーネント自体を非表示にする。 */
export const EmptyState: Story = {
    args: { notices: [] },
};

/** カスタムカラー */
export const CustomColors: Story = {
    args: {
        notices: [MOCK_NOTICES[1]],
        label: '緊急',
        labelColor: '#FF4444',
        titleColor: '#FFFF00',
        contentColor: '#EEEEEE',
        bgColor: '#1A1A2E',
    },
};
